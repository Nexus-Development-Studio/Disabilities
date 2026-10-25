package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.object.Disability;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.function.Consumer;

public class Blind extends Disability {
    @Override
    public String getId() {
        return "blind";
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
        return "You cannot see anything.";
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3, 1));
    }

    @Override
    public long getDelay() {
        return 10L;
    }

    @Override
    public String getName() {
        return "Blind";
    }
}
