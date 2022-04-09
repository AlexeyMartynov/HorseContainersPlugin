package io.github.alexeymartynov.horsecontainers.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UtilItem {

	public static boolean areTheSameItems(ItemStack firstItem, ItemStack secondItem) 
	{
		if(firstItem != null && secondItem != null) 
		{
			if(firstItem.getType() == secondItem.getType() && Util.getStringWithoutColor(firstItem.getItemMeta().
					toString()).equals(Util.getStringWithoutColor(secondItem.getItemMeta().toString())))

				return true;
		}
		
		return false;
	}
}
