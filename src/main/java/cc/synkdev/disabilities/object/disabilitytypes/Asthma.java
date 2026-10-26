package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.manager.PlayerManager;
import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class Asthma extends Disability {
    @Override
    public String getId() {
        return "asthma";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[]{PlayerToggleSprintEvent.class, PlayerQuitEvent.class};
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[]{event -> {
            PlayerToggleSprintEvent e = (PlayerToggleSprintEvent) event;
            if (!isDisabled(e.getPlayer())) return;
            Player p = e.getPlayer();
            if (e.isSprinting()) {
                PlayerManager.getSprintMap().put(p.getUniqueId(), System.currentTimeMillis());
            } else {
                PlayerManager.getSprintMap().remove(p.getUniqueId());
            }
        }, o -> {
            PlayerQuitEvent e = (PlayerQuitEvent) o;
            PlayerManager.getSprintMap().remove(e.getPlayer().getUniqueId());
        }};
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            if (!isDisabled(p)) return;
            long diff = System.currentTimeMillis() - PlayerManager.getSprintMap().getOrDefault(p.getUniqueId(), System.currentTimeMillis());
            if (diff > 30000) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 1));
                Utils.sendMessage(p, "<red>You have asthma! Sprinting for too long will make you suffocate.");
                p.setSprinting(false);
                PlayerManager.getSprintMap().remove(p.getUniqueId());
            }
        };
    }

    @Override
    public long getDelay() {
        return 20L;
    }

    @Override
    public String getDescription() {
        return "Eating too much sugar will poison you.";
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public String getName() {
        return "Asthma";
    }
}
