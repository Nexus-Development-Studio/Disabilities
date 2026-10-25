package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Seizures extends Disability {
    @Override
    public String getId() {
        return "seizures";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[0];
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[0];
    }

    @Override
    public String getDescription() {
        return "Low health or hunger can cause you to have seizures.";
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            boolean damage = p.getHealth() < 6.0;
            boolean hunger = p.getFoodLevel() < 6;
            if (!damage && !hunger) {
                return;
            }
            double value;
            if (damage && hunger) {
                value = 1.2;
            } else {
                value = 1.0;
            }

            if (Math.random()*value >= 0.85) {
                Utils.sendMessage(p, "<red>You are having a seizure!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 1));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 20*5, 1));
                AtomicInteger rep = new AtomicInteger();
                Bukkit.getScheduler().runTaskTimer(Disabilities.getInstance(), () -> {
                    rep.addAndGet(1);
                    if (rep.get() > 100) return;
                    p.teleport(p.getLocation().addRotation(Math.round(Math.random()*4 - 2), Math.round(Math.random()*4 - 2)));
                }, 0L, 2L);
            }
        };
    }

    @Override
    public long getDelay() {
        return 20 * 60L;
    }

    @Override
    public String getName() {
        return "Seizures";
    }
}
