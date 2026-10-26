package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.function.Consumer;

public class Schizophrenia extends Disability {
    @Override
    public String getId() {
        return "schizophrenia";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[0];
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[0];
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            p.playSound(p.getLocation(), Registry.SOUNDS.stream().toList().get(Math.toIntExact(Math.round(Math.random()*(Registry.SOUNDS.size()-1)))), 1f, 1f);
        };
    }

    @Override
    public long getDelay() {
        return 20*60L;
    }

    @Override
    public String getName() {
        return "Schizophrenia";
    }

    @Override
    public String getDescription() {
        return "You hear sounds of things that aren't there.";
    }
}

