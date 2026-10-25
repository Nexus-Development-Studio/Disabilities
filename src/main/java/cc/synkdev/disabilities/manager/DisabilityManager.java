package cc.synkdev.disabilities.manager;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.object.Disability;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DisabilityManager {
    private DisabilityManager() {
        /* This utility class should not be instantiated */
    }

    @Getter
    private static final List<Disability> dTypes = new ArrayList<>();
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
}
