package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class Mute extends Disability {
    @Override
    public String getId() {
        return "mute";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[]{AsyncChatEvent.class};
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[]{event -> {
            AsyncChatEvent e = (AsyncChatEvent) event;
            e.setCancelled(true);
            Utils.sendMessage(e.getPlayer(), "<red>You are muted and cannot send messages.");
        }};
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return null;
    }

    @Override
    public long getDelay() {
        return 0L;
    }

    @Override
    public String getDescription() {
        return "You cannot talk in the chat.";
    }

    @Override
    public String getName() {
        return "Mute";
    }
}
