package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Adhd extends Disability {
    @Override
    public String getId() {
        return "adhd";
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
        return "Items randomly move in your inventory.";
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;

            if (Math.random() < 0.85) {
                return;
            }

            p.getInventory().setHeldItemSlot(Math.toIntExact(Math.round(Math.random()*8)));
        };
    }

    @Override
    public long getDelay() {
        return 30 * 20L;
    }

    @Override
    public String getName() {
        return "ADHD";
    }
}
