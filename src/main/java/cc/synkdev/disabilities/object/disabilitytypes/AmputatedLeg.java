package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.object.Disability;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.function.Consumer;

public class AmputatedLeg extends Disability {
    @Override
    public String getId() {
        return "amputatedleg";
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
        return "You cannot move without jumping.";
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            p.setWalkSpeed(0f);
        };
    }

    @Override
    public long getDelay() {
        return 10L;
    }

    @Override
    public Runnable reset(Player p) {
        return () -> p.setWalkSpeed(0.2f);
    }

    @Override
    public String getName() {
        return "Amputated Leg";
    }
}
