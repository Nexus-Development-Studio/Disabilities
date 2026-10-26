package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.manager.PlayerManager;
import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.function.Consumer;

public class Narcolepsy extends Disability {
    @Override
    public String getId() {
        return "narcolepsy";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[]{PlayerMoveEvent.class};
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[]{event -> {
            PlayerMoveEvent e = (PlayerMoveEvent) event;
            if (!isDisabled(e.getPlayer())) return;
            Player p = e.getPlayer();
            if (PlayerManager.getSleepMap().contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }};
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            if (!isDisabled(p)) return;
            if (Math.random() < 0.85) {
                return;
            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 1));
            PlayerManager.getSleepMap().add(p.getUniqueId());
            Utils.sendMessage(p, "<red>You have narcolepsy! You randomly fall asleep.");
            p.setPose(Pose.SLEEPING, true);
            Bukkit.getScheduler().runTaskLater(Disabilities.getInstance(), () -> {
                p.setPose(Pose.STANDING, true);
                PlayerManager.getSleepMap().remove(p.getUniqueId());
            }, 20*5L);
        };
    }

    @Override
    public long getDelay() {
        return 20*60L;
    }

    @Override
    public String getDescription() {
        return "You randomly fall asleep.";
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public String getName() {
        return "Narcolepsy";
    }
}
