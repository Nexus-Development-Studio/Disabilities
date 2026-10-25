package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.object.Disability;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.UUID;
import java.util.function.Consumer;

public class Deaf extends Disability {
    @Override
    public String getId() {
        return "deaf";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[0];
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[0];
    }

    private final UUID uuid = UUID.nameUUIDFromBytes("disabilities:deaf".getBytes(StandardCharsets.UTF_8));
    private final String url = "https://synkdev.cc/packs/deaf.zip";
    private final String hash = "7cbcfa7766a3b4997770e6cddd562bc64cac7438";

    @Override
    public String getDescription() {
        return "You cannot hear anything.";
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (first) {
                p.addResourcePack(uuid, url, HexFormat.of().parseHex(hash), "This resource pack is required to simulate the deafness disability.", true);
            }
        };
    }

    @Override
    public long getDelay() {
        return 20*60L;
    }

    @Override
    public String getName() {
        return "Deaf";
    }
}
