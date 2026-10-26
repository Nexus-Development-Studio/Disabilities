package cc.synkdev.disabilities;

import cc.synkdev.disabilities.command.DisabilityCommand;
import cc.synkdev.disabilities.manager.ConfigManager;
import cc.synkdev.disabilities.manager.DisabilityManager;
import cc.synkdev.disabilities.manager.FileManager;
import cc.synkdev.disabilities.manager.PlayerManager;
import cc.synkdev.disabilities.object.Disability;
import co.aikar.commands.PaperCommandManager;
import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import dev.faststats.bukkit.BukkitContext;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Disabilities extends JavaPlugin {
    @Getter private static Disabilities instance;
    public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();
    private final BukkitContext context = new BukkitContext.Factory(this, "c0940e18db51b5f1b30710c2602ceb6f")
            .errorTrackerService(ERROR_TRACKER)
            .metrics(Metrics.Factory::create)
            .create();

    @Override
    public void onEnable() {
        instance = this;
        context.ready();

        ConfigManager.load();
        DisabilityManager.registerTypes(this);

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.getCommandCompletions().registerCompletion("disabilities", c -> DisabilityManager.getDTypes().stream().map(Disability::getId).toList());
        commandManager.registerCommand(new DisabilityCommand());

        DisabilityManager.registerTask();
        FileManager.load();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(PlayerManager::sendScoreboard);
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        context.shutdown();
    }

    public Component getPrefix() {
        return MiniMessage.miniMessage().deserialize("<dark_gray>[<gradient:#00ff00:#00ffff>Disabilities</gradient><dark_gray>] » <reset>");
    }
}
