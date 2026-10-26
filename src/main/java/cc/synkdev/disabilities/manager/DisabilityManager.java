package cc.synkdev.disabilities.manager;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.command.DisabilityCommand;
import cc.synkdev.disabilities.object.Disability;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisabilityManager {
    private DisabilityManager() {
        /* This utility class should not be instantiated */
    }
    @Getter private static int taskId;

    @Getter
    private static final List<Disability> dTypes = new ArrayList<>();
    @Getter @Setter private static long timer;
    public static void registerTypes(Disabilities core) {
        try (ScanResult result = new ClassGraph()
                .enableAllInfo()
                .acceptPackages("cc.synkdev")
                .addClassLoader(Disability.class.getClassLoader()).scan()) {
            List<Class<?>> list = result.getSubclasses(Disability.class.getName()).loadClasses();

            for (Class<?> clazz : list) {
                Disability type = (Disability) clazz.getDeclaredConstructor().newInstance();
                Utils.debug("Registered pType " + type.getId());
                type.registerListener();
                DisabilityManager.getDTypes().add(type);
                if (type.execute(null, false) != null) {
                    Bukkit.getScheduler().runTaskTimer(core, () -> {
                        PlayerManager.getPlayerMap().entrySet().stream()
                                .filter(entry -> entry.getValue().contains(type.getId()))
                                .forEach(entry -> type.execute(Bukkit.getPlayer(entry.getKey()), false).run());
                    }, 0L, type.getDelay());
                }
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public static void registerTask() {
        taskId = Bukkit.getScheduler().runTaskTimer(Disabilities.getInstance(), () -> {
            DisabilityCommand.assignDisabilities();
            timer = System.currentTimeMillis()/1000+ConfigManager.getTimer()/1000;
        }, ConfigManager.getTimer()/1000*20L, ConfigManager.getTimer()/1000*20L).getTaskId();
        timer = System.currentTimeMillis()/1000+ConfigManager.getTimer()/1000;
    }
}
