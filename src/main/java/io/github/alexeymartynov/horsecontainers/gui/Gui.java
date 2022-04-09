package io.github.alexeymartynov.horsecontainers.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Gui extends GuiHolder { 

	public Gui(String title, int size) { super(title, size); }
	
	public abstract void onGuiClick(InventoryClickEvent event);
	
	public abstract void create(Player player);
	
	public void open(Player player) 
	{  
		create(player);
		player.openInventory(inventory);
	}
}
