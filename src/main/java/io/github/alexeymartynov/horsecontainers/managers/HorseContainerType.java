package io.github.alexeymartynov.horsecontainers.managers;

import io.github.alexeymartynov.horsecontainers.gui.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum HorseContainerType {

    LEVEL_FIRST(9),
    LEVEL_SECOND(18),
    LEVEL_THIRD(27),
    LEVEL_FOURTH(36);

    private int slotCount;
    private ItemStack item = new ItemStack(Material.CHEST);

    public void initialize()
    {
        List<String> lore = new ArrayList<>();
        int level = 0;
        for(HorseContainerType horseContainerType : HorseContainerType.values())
        {
            if(horseContainerType == this)
                break;

            level++;
        }

        level += 1;
        lore.add(ChatColor.GREEN + "Уровень контейнера - " + ChatColor.GOLD + level);
        lore.add(ChatColor.WHITE + "Чтобы повесить на лошадь - нажмите по ней ПКМ, держа в руках данный предмет");
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.YELLOW + "Лошадиный контейнер");
        item.setItemMeta(meta);
        this.item = item;
    }

    private HorseContainerType(int slotCount) { this.slotCount = slotCount; }

    public ItemStack getItem() { return item; }

    public int getSlotCount() { return slotCount; }

    public static HorseContainerType getHorseContainerType(ItemStack item)
    {
        HorseContainerType horseContainerType;
        try
        {
            horseContainerType = Arrays.stream(HorseContainerType.values()).filter(value ->
                    UtilItem.areTheSameItems(value.getItem(), item)).findFirst().get();
        }
        catch (Exception exception) { return null; }

        return horseContainerType;
    }
}
