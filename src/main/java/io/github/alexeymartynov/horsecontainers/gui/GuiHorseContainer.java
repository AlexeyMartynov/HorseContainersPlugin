package io.github.alexeymartynov.horsecontainers.gui;

import io.github.alexeymartynov.horsecontainers.main.HorseContainersPlugin;
import io.github.alexeymartynov.horsecontainers.managers.HorseContainer;
import io.github.alexeymartynov.horsecontainers.managers.HorseContainerType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class GuiHorseContainer extends Gui {

    private UUID horseId;

    public GuiHorseContainer(HorseContainerType horseContainerType, UUID horseId)
    {
        super("Контейнер", horseContainerType.getSlotCount());
        this.horseId = horseId;
    }

    @Override
    public void onGuiClick(InventoryClickEvent event) {}

    @Override
    public void create(Player player)
    {
        HorseContainer horseContainer = HorseContainersPlugin.getInstance().
                getHorseContainersManager().getHorseContainer(horseId);

        if(horseContainer == null)
            return;

        List<ItemStack> items = horseContainer.getItems();
        if(items.isEmpty())
            return;

        int index = 0;
        for(ItemStack item : items)
        {
            inventory.setItem(index, item);
            index++;
        }
    }

    public UUID getHorseId() { return horseId; }
}
