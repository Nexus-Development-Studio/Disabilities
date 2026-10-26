package cc.synkdev.disabilities.object;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class Disability implements Listener {
    public abstract String getId();

    public abstract Class<? extends Event>[] eventClasses();

    public abstract Consumer<Event>[] handleEvent();

    public abstract Runnable execute(Player p, boolean first);

    public abstract long getDelay();

    public abstract String getName();
    public abstract String getDescription();
    public abstract Runnable reset(Player p);

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Disability type && type.getId().equalsIgnoreCase(getId());
    }

    public void registerListener() {
        if (eventClasses() == null || handleEvent() == null) return;
        EventExecutor executor = (_, event) -> {
            int i = 0;
            for (Class<? extends Event> aClass : eventClasses()) {
                if (aClass.isInstance(event)) {
                    handleEvent()[i].accept(event);
                }
                i = i+1;
            }

        };
        for (Class<? extends Event> aClass : eventClasses()) {
            Bukkit.getPluginManager().registerEvent(aClass, this, EventPriority.HIGH, executor, Disabilities.getInstance());
        }
    }
    public boolean isDisabled(Player p) {
        return p != null && PlayerManager.getPlayerMap().getOrDefault(p.getUniqueId(), new ArrayList<>()).contains(getId());
    }
}
