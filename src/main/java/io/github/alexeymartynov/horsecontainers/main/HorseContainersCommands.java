package io.github.alexeymartynov.horsecontainers.main;

import io.github.alexeymartynov.horsecontainers.managers.HorseContainerType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HorseContainersCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player))
            return true;

        HorseContainerType horseContainerType;
        try { horseContainerType = HorseContainerType.values()[Integer.parseInt(args[0]) - 1]; }
        catch (Exception exception) { horseContainerType = HorseContainerType.LEVEL_FIRST; }

        ((Player) sender).getInventory().addItem(horseContainerType.getItem());
        return true;
    }
}
