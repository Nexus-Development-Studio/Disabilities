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
}
