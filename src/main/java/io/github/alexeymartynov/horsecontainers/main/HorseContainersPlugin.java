package io.github.alexeymartynov.horsecontainers.main;

import io.github.alexeymartynov.horsecontainers.handlers.HorseContainersHandler;
import io.github.alexeymartynov.horsecontainers.managers.HorseContainerType;
import io.github.alexeymartynov.horsecontainers.managers.HorseContainersManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HorseContainersPlugin extends JavaPlugin {

    private static HorseContainersPlugin instance;

    private HorseContainersManager horseContainersManager;
    private HorseContainersHandler horseContainersHandler;

    @Override
    public void onEnable()
    {
        instance = this;
        horseContainersManager = new HorseContainersManager();
        horseContainersHandler = new HorseContainersHandler();
        Bukkit.getPluginManager().registerEvents(horseContainersHandler, getInstance());

        getCommand("containerget").setExecutor(new HorseContainersCommands());

        for(HorseContainerType horseContainerType : HorseContainerType.values())
            horseContainerType.initialize();

        Bukkit.getLogger().severe("--------- HorseContainersPlugin by bybyzyanka ---------");
        Bukkit.getLogger().severe("-- VK: https://vk.com/kai9595 -------------------------");
        Bukkit.getLogger().severe("-- Discord: ! alexxxey#1903 ---------------------------");
        Bukkit.getLogger().severe("-- GitHub: https://github.com/AlexeyMartynov ----------");
        Bukkit.getLogger().severe("-------------------------------------------------------");
    }

    @Override
    public void onDisable() {}

    public static HorseContainersPlugin getInstance() { return instance; }

    public HorseContainersManager getHorseContainersManager() { return horseContainersManager; }
}
