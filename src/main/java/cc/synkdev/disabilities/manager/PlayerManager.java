package cc.synkdev.disabilities.manager;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.object.Disability;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class PlayerManager {
    private PlayerManager() {
        /* This utility class should not be instantiated */
    }

    @Getter private static final Map<UUID, List<String>> playerMap = new HashMap<>();
    @Getter private static final Map<UUID, Long> sprintMap = new HashMap<>();
    @Getter private static final List<UUID> sleepMap = new ArrayList<>();
    public static void disable(OfflinePlayer player, Disability disability) {
        List<String> list = playerMap.getOrDefault(player.getUniqueId(), new ArrayList<>());
        if (!list.contains(disability.getId())) list.add(disability.getId());
        playerMap.put(player.getUniqueId(), list);
        FileManager.save();
        if (player.isOnline() && disability.execute(player.getPlayer(), true) != null) disability.execute(player.getPlayer(), true).run();
        TextComponent name = Component.text(disability.getName()).hoverEvent(HoverEvent.showText(Component.text(disability.getDescription(), NamedTextColor.YELLOW)));
        if (player.isOnline()) {
            assert player.getPlayer() != null;
            Utils.sendMessage(player.getPlayer(), Component.text("You have been given the ", NamedTextColor.RED).append(name).append(Component.text(" disability.", NamedTextColor.RED)));
        }
    }

    public static void reset(Player target) {
        getPlayerMap().remove(target.getUniqueId());
        FileManager.save();
        DisabilityManager.getDTypes().forEach(disability -> {
            if (disability.reset(target) != null) disability.reset(target).run();
        });
        target.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }
    public static void sendScoreboard(Player p) {
        List<String> list = getPlayerMap().getOrDefault(p.getUniqueId(), new ArrayList<>());
        if (list.isEmpty()) return;
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("disabilities", Criteria.DUMMY, Component.text("Disabilities", NamedTextColor.RED));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Collections.sort(list);
        Collections.reverse(list);
        int topI = 0;
        for (var ref = new Object() {
            int i = 2;
        }; ref.i < list.size()+2; ref.i++) {
            obj.getScore(DisabilityManager.getDTypes().stream().filter(disability -> disability.getId().equalsIgnoreCase(list.get(ref.i-2))).findFirst().orElse(null).getName()).setScore(ref.i);
            topI = ref.i;
        }
        obj.getScore("  ").setScore(topI + 1);
        obj.getScore("  ").setScore(1);
        obj.getScore("§cNext disability in: "+Utils.getTimer()).setScore(0);

        p.setScoreboard(board);
    }

    public static void enable(OfflinePlayer target, Disability d) {
        List<String> list = getPlayerMap().getOrDefault(target.getUniqueId(), new ArrayList<>());
        list.remove(d.getId());
        getPlayerMap().put(target.getUniqueId(), list);
        FileManager.save();
        if (target.isOnline() && d.execute(target.getPlayer(), false) != null) d.execute(target.getPlayer(), false).run();
        TextComponent name = Component.text(d.getName()).hoverEvent(HoverEvent.showText(Component.text(d.getDescription(), NamedTextColor.YELLOW)));
        if (target.isOnline()) {
            assert target.getPlayer() != null;
            Utils.sendMessage(target.getPlayer(), Component.text("You have been removed the ", NamedTextColor.RED).append(name).append(Component.text(" disability.", NamedTextColor.RED)));
        }
    }
}
