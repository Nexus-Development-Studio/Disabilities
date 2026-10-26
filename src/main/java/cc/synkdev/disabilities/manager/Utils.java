package cc.synkdev.disabilities.manager;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.object.Disability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Utils {
    private Utils() {
        /* This utility class should not be instantiated */
    }


    public static void log(Component message) {
        Bukkit.getConsoleSender().sendMessage(Disabilities.getInstance().getPrefix().append(message));
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Disabilities.getInstance().getPrefix().append(MiniMessage.miniMessage().deserialize(message)));
    }

    public static void debug(String message) {
        if (ConfigManager.isDebug()) log(MiniMessage.miniMessage().deserialize("<dark_gray>[DEBUG] » " + message));
    }

    public static void debug(Component component) {
        if (ConfigManager.isDebug())
            log(MiniMessage.miniMessage().deserialize("<dark_gray>[DEBUG] » ").append(component));
    }

    public static void sendMessage(CommandSender sender, String s) {
        sender.sendMessage(Disabilities.getInstance().getPrefix().append(minify(s)));
    }

    public static void sendMessage(CommandSender sender, Component comp) {
        sender.sendMessage(Disabilities.getInstance().getPrefix().append(comp));
    }

    public static Component minify(String s) {
        return MiniMessage.miniMessage().deserialize(s);
    }

    public static Disability getDisability(String id) {
        return DisabilityManager.getDTypes().stream().filter(d -> d.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public static String getTimer() {
        return parseDuration(DisabilityManager.getTimer() - System.currentTimeMillis()/1000);
    }

    public static long parseTime(String time) {
        if (time.equalsIgnoreCase("0")) return -1;
        long totalMillis = 0;
        if (time.endsWith("h")) {
            totalMillis += Long.parseLong(time.replace("h", "")) * 60 * 60;
        } else if (time.endsWith("m")) {
            totalMillis += Long.parseLong(time.replace("m", "")) * 60;
        } else if (time.endsWith("s")) {
            totalMillis += Long.parseLong(time.replace("s", ""));
        } else if (time.endsWith("d")) {
            totalMillis += Long.parseLong(time.replace("d", "")) * 24 * 60 * 60;
        } else if (time.endsWith("w")) {
            totalMillis += Long.parseLong(time.replace("w", "")) * 7 * 24 * 60 * 60;
        } else if (time.endsWith("mo")) {
            totalMillis += Long.parseLong(time.replace("mo", "")) * 30 * 24 * 60 * 60;
        } else if (time.endsWith("y")) {
            totalMillis += Long.parseLong(time.replace("y", "")) * 365 * 24 * 60 * 60;
        }
        Utils.debug("Parsed time string: " + time + " to " + totalMillis + " seconds.");
        return totalMillis * 1000;
    }

    public static String parseDuration(long time) {
        if (time < 0) return  "Timer disabled";
        long seconds = time %60;
        long minutes = (time / 60) % 60;
        if (minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d", seconds);
        }
    }
}
