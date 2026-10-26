package cc.synkdev.disabilities.manager;

import cc.synkdev.disabilities.Disabilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManager {
    private FileManager() {
        /* This utility class should not be instantiated */
    }

    private static final File file = new File(Disabilities.getInstance().getDataFolder(), "data.json");
    public static void load() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                Files.writeString(file.toPath(), "[]");
            }
            Utils.debug("Loading data from " + file.getAbsolutePath());
            PlayerManager.getPlayerMap().clear();

            JSONArray players = new JSONArray(Files.readString(file.toPath()));
            for (Object playerObj : players) {
                JSONObject player = (JSONObject) playerObj;
                List<String> list = new ArrayList<>();
                player.getJSONArray("disabilities").forEach(disability -> list.add((String) disability));
                Utils.debug("Loading player data " + player.getString("uuid") + " with disabilities " + list);
                PlayerManager.getPlayerMap().put(UUID.fromString(player.getString("uuid")), list);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void save() {
        try {
            JSONArray players = new JSONArray();
            PlayerManager.getPlayerMap().forEach((uuid, disabilities) -> {
                JSONObject player = new JSONObject();
                player.put("uuid", uuid.toString());
                player.put("disabilities", new JSONArray(disabilities));
                players.put(player);
            });
            Files.writeString(file.toPath(), players.toString(2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
