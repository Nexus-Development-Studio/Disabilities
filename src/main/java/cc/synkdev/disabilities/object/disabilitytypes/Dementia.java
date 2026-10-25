package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.Disabilities;
import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Dementia extends Disability {
    @Override
    public String getId() {
        return "dementia";
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
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (p == null) return;
            if (p.getInventory().firstEmpty() == -1) return;

            if (Math.random() < 0.85) {
                return;
            }

            int i = 0;
            Map<Integer, ItemStack> map = new HashMap<>();
            for (ItemStack content : p.getInventory().getContents()) {
                if (i >= 36) break;
                map.put(i, content);
                i++;
            }

            List<Map.Entry<Integer, ItemStack>> list = new ArrayList<>(map.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && entry.getValue().getType() != Material.AIR).toList());
            Collections.shuffle(list);
            Map.Entry<Integer, ItemStack> setItem = list.getFirst();

            List<Map.Entry<Integer, ItemStack>> emptyList = new ArrayList<>(map.entrySet().stream()
                    .filter(entry -> entry.getValue() == null || entry.getValue().getType() == Material.AIR).toList());
            Collections.shuffle(emptyList);
            int dest = emptyList.getFirst().getKey();
            p.getInventory().setItem(setItem.getKey(), new ItemStack(Material.AIR));
            p.getInventory().setItem(dest, setItem.getValue());
        };
    }

    @Override
    public long getDelay() {
        return 10 * 20L;
    }

    @Override
    public String getName() {
        return "Dementia";
    }
}
