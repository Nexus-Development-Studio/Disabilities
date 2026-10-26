package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Diabetes extends Disability {
    @Override
    public String getId() {
        return "diabetes";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[]{PlayerItemConsumeEvent.class};
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        final Map<Material, Integer> sweetness = new EnumMap<>(Map.of(
                Material.CAKE, 3,
                Material.COOKIE, 3,
                Material.PUMPKIN_PIE, 3,
                Material.HONEY_BOTTLE, 3,
                Material.APPLE, 2,
                Material.GOLDEN_APPLE, 2,
                Material.ENCHANTED_GOLDEN_APPLE, 2,
                Material.SWEET_BERRIES, 2,
                Material.GLOW_BERRIES, 2,
                Material.MELON_SLICE, 2
        ));
        sweetness.putAll(Map.of(
                Material.CARROT, 1,
                Material.GOLDEN_CARROT, 1,
                Material.BEETROOT, 1,
                Material.CHORUS_FRUIT, 1
        ));
        return new Consumer[]{event -> {
            PlayerItemConsumeEvent e = (PlayerItemConsumeEvent) event;
            if (!isDisabled(e.getPlayer())) return;
            Player p = e.getPlayer();
            if (e.getItem().getType().isEdible() && sweetness.containsKey(e.getItem().getType())) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*10, sweetness.get(e.getItem().getType())));
                Utils.sendMessage(p, "<red>You have diabetes! Eating too much sugar will damage you.");
            }
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
        return "Eating too much sugar will poison you.";
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public String getName() {
        return "Diabetes";
    }
}
