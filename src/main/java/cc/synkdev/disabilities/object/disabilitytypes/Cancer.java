package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Cancer extends Disability {
    @Override
    public String getId() {
        return "cancer";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[0];
    }

    @Override
    public Runnable reset(Player p) {
        return () -> Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(20.0);
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[0];
    }

    @Override
    public String getDescription() {
        return "Your max health slowly decreases over time.";
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            if (Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() > 6.0 && Math.random() >= 0.85) {
                Utils.sendMessage(p, "<red>Cancer is slowly eating away your max health!");
                Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() - 1.0);
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
