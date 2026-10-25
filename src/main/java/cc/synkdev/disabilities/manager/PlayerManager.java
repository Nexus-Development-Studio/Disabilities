package cc.synkdev.disabilities.manager;

import cc.synkdev.disabilities.object.Disability;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class PlayerManager {
    private PlayerManager() {
        /* This utility class should not be instantiated */
    }

    @Getter private static final Map<UUID, List<String>> playerMap = new HashMap<>();
    public static void disable(OfflinePlayer player, Disability disability) {
        List<String> list = playerMap.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (!list.contains(disability.getId())) list.add(disability.getId());
        playerMap.put(player.getUniqueId(), list);
        if (player.isOnline() && disability.execute(player.getPlayer(), true) != null) disability.execute(player.getPlayer(), true).run();
        TextComponent name = Component.text(disability.getName()).hoverEvent(HoverEvent.showText(Component.text(disability.getDescription(), NamedTextColor.YELLOW)));
        if (player.isOnline()) Utils.sendMessage(player.getPlayer(), Component.text("You have been given the ", NamedTextColor.RED).append(name).append(Component.text(" disability.", NamedTextColor.RED)));
    }
}
