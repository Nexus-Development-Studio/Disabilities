package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.object.Disability;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class BrainPalsy extends Disability {
    @Override
    public String getId() {
        return "brainpalsy";
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
        return "You randomly move or click.";
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            if (!isDisabled(p)) return;

            if (Math.random() >= 0.85) {
                p.teleport(p.getLocation().add(Math.random()*0.5-0.25, 0, Math.random()*0.5-0.25));
            }

            if (Math.random() >= 0.85) {
                p.swingMainHand();
            }

            if (Math.random() >= 0.85) {
                p.swingOffHand();
            }

        };
    }

    @Override
    public long getDelay() {
        return 20 * 20L;
    }

    @Override
    public String getName() {
        return "Brain Palsy";
    }
}
