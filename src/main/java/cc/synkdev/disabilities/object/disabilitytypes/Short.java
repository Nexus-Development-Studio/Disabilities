package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Short extends Disability {
    @Override
    public String getId() {
        return "cancer";
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
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            if (Objects.requireNonNull(p.getAttribute(Attribute.SCALE)).getBaseValue() >= 1.0) {
                Utils.sendMessage(p, "<red>You are now short!");
                Objects.requireNonNull(p.getAttribute(Attribute.SCALE)).setBaseValue(0.83);
                Objects.requireNonNull(p.getAttribute(Attribute.BLOCK_INTERACTION_RANGE)).setBaseValue(3.5);
                Objects.requireNonNull(p.getAttribute(Attribute.ENTITY_INTERACTION_RANGE)).setBaseValue(2.5);
            }
        };
    }

    @Override
    public String getDescription() {
        return "You get shorter and you reach is reduced.";
    }

    @Override
    public long getDelay() {
        return 20 * 60L;
    }

    @Override
    public String getName() {
        return "Short";
    }
}
