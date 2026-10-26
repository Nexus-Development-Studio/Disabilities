package cc.synkdev.disabilities.object.disabilitytypes;

import cc.synkdev.disabilities.manager.Utils;
import cc.synkdev.disabilities.object.Disability;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.function.Consumer;

public class AmputatedHand extends Disability {
    @Override
    public String getId() {
        return "amputatedhand";
    }

    @Override
    public Class<? extends Event>[] eventClasses() {
        return new Class[]{InventoryClickEvent.class, PlayerSwapHandItemsEvent.class};
    }

    @Override
    public String getDescription() {
        return "You cannot use your offhand.";
    }

    @Override
    public Runnable reset(Player p) {
        return null;
    }

    @Override
    public Consumer<Event>[] handleEvent() {
        return new Consumer[]{event -> {
            InventoryClickEvent e = (InventoryClickEvent) event;
            if (!isDisabled((Player) e.getWhoClicked())) return;
            if (e.getClickedInventory() != e.getWhoClicked().getInventory() || !(e.getSlot() == 40 || e.getClick() == ClickType.SWAP_OFFHAND))
                return;
            e.getWhoClicked().getInventory().addItem(e.getCursor());
            e.setCancelled(true);
            Utils.sendMessage(e.getWhoClicked(), "<red>Your offhand has been amputated!");
        }, o -> {
            PlayerSwapHandItemsEvent e = (PlayerSwapHandItemsEvent) o;
            if (!isDisabled(e.getPlayer())) return;
            e.setCancelled(true);
            Utils.sendMessage(e.getPlayer(), "<red>Your offhand has been amputated!");
        }};
    }

    @Override
    public Runnable execute(Player p, boolean first) {
        return () -> {
            if (!first) return;
            if (p.getInventory().getItemInOffHand().getType() == Material.AIR) return;
            p.getLocation().getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getItemInOffHand());
            p.getInventory().setItemInOffHand(null);
        };
    }

    @Override
    public long getDelay() {
        return 0L;
    }

    @Override
    public String getName() {
        return "Amputated Offhand";
    }
}
