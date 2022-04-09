package io.github.alexeymartynov.horsecontainers.handlers;

import io.github.alexeymartynov.horsecontainers.gui.GuiHorseContainer;
import io.github.alexeymartynov.horsecontainers.gui.UtilItem;
import io.github.alexeymartynov.horsecontainers.main.HorseContainersPlugin;
import io.github.alexeymartynov.horsecontainers.main.Message;
import io.github.alexeymartynov.horsecontainers.managers.HorseContainer;
import io.github.alexeymartynov.horsecontainers.managers.HorseContainerType;
import io.github.alexeymartynov.horsecontainers.managers.HorseContainersManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HorseContainersHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHorseRightClick(PlayerInteractAtEntityEvent event)
    {
        Entity entity = event.getRightClicked();
        if(entity.getType() != EntityType.HORSE || !event.getPlayer().isSneaking() || !((Horse) entity).isTamed())
            return;

        Player player = event.getPlayer();
        UUID horseId = entity.getUniqueId();
        ItemStack item = player.getInventory().getItemInMainHand();
        HorseContainersManager horseContainersManager = HorseContainersPlugin.getInstance().getHorseContainersManager();
        if(item == null)
            return;

        for(HorseContainerType horseContainerType : HorseContainerType.values())
        {
            if (UtilItem.areTheSameItems(horseContainerType.getItem(), item))
            {
                if(!horseContainersManager.create(event.getPlayer().getName(), horseId, horseContainerType))
                {
                    player.sendMessage(Message.HORSE_ALREADY_HAS_CONTAINER.toString());
                    return;
                }

                player.getInventory().remove(item);
                player.sendMessage(Message.CONTAINER_ADDED.toString());
                return;
            }
        }
    }

    @EventHandler
    public void onContainerClose(InventoryCloseEvent event)
    {
        if(event.getInventory().getHolder() instanceof GuiHorseContainer)
        {
            List<ItemStack> items = new ArrayList<>();
            Arrays.stream(event.getInventory().getContents()).filter(item -> item != null).
                    forEach(item -> items.add(item));

            HorseContainersPlugin.getInstance().getHorseContainersManager().
                    getHorseContainer(((GuiHorseContainer) event.getInventory().getHolder()).getHorseId()).
                    setItems(items);
        }
    }

    @EventHandler
    public void onHorseDeath(EntityDeathEvent event)
    {
        Entity entity = event.getEntity();
        if(entity.getType() != EntityType.HORSE)
            return;

        HorseContainersManager horseContainersManager = HorseContainersPlugin.getInstance().getHorseContainersManager();
        HorseContainer horseContainer = horseContainersManager.getHorseContainer(entity.getUniqueId());
        if(!horseContainersManager.remove(horseContainer))
            return;

        Location location = entity.getLocation();
        for(ItemStack item : horseContainer.getItems())
            if(item != null)
                location.getWorld().dropItem(location, item);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        if(HorseContainerType.getHorseContainerType(event.getPlayer().getInventory().getItemInMainHand()) != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onHorseGuiOpening(InventoryOpenEvent event)
    {
        if(event.getInventory() instanceof HorseInventory && ((Player) event.getPlayer()).isSneaking())
        {
            HorseContainer horseContainer = HorseContainersPlugin.getInstance().getHorseContainersManager().getHorseContainer(
                    ((Entity) ((HorseInventory) event.getInventory()).getHolder()).getUniqueId());

            if(horseContainer != null && horseContainer.getOwnerNick().equals(event.getPlayer().getName()))
            {
                event.setCancelled(true);
                new GuiHorseContainer(horseContainer.getContainerType(),
                        horseContainer.getHorseId()).open((Player) event.getPlayer());
            }
        }
    }
}
