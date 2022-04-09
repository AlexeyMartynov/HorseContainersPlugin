package io.github.alexeymartynov.horsecontainers.managers;

import io.github.alexeymartynov.horsecontainers.main.HorseContainersPlugin;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HorseContainer {

    private String ownerNick;
    private UUID horseId;
    private HorseContainerType containerType;
    private List<ItemStack> items = new ArrayList<>();

    public HorseContainer(String ownerNick, UUID horseId, HorseContainerType containerType, List<ItemStack> items)
    {
        this.ownerNick = ownerNick;
        this.horseId = horseId;
        this.containerType = containerType;
        this.items = items;
    }

    public String getOwnerNick() { return ownerNick; }

    public UUID getHorseId() { return horseId; }

    public List<ItemStack> getItems() { return items; }

    public HorseContainerType getContainerType() { return containerType; }

    public void setItems(List<ItemStack> items)
    {
        this.items = items;
        HorseContainersPlugin.getInstance().getHorseContainersManager().sync();
    }
}
