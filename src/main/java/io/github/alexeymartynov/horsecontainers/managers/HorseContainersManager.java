package io.github.alexeymartynov.horsecontainers.managers;

import io.github.alexeymartynov.horsecontainers.main.HorseContainersPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HorseContainersManager {

    private FileConfiguration config;
    private File file = new File(HorseContainersPlugin.getInstance().getDataFolder()
            + File.separator + "horse_containers.yml");

    private List<HorseContainer> horseContainers = new ArrayList<>();

    public HorseContainersManager()
    {
        HorseContainersPlugin.getInstance().getDataFolder().mkdir();
        if(!file.exists())
        {
            try { file.createNewFile(); }
            catch (Exception exception) {}

            config = YamlConfiguration.loadConfiguration(file);
            config.createSection("containers");

            try { config.save(file); }
            catch (Exception exception) {}
        }
        else config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = config.getConfigurationSection("containers");
        if(section == null)
            return;

        for(String horseId : section.getKeys(false))
        {
            ConfigurationSection itemsSection = section.getConfigurationSection(horseId + ".items");
            List<ItemStack> items = new ArrayList<>();
            if(itemsSection != null)
            {
                itemsSection.getKeys(false).stream().forEach(index ->
                {
                    {
                        ItemStack item = itemsSection.getItemStack(index);
                        if(item != null)
                            items.add(item);
                    }
                });
            }

            horseContainers.add(new HorseContainer(section.getString(horseId + ".owner_nick"),
                    UUID.fromString(horseId),
                    HorseContainerType.valueOf(section.getString(horseId + ".container_type")),
                    items));
        }
    }

    public boolean create(String ownerNick, UUID horseId, HorseContainerType horseContainerType)
    {
        if(getHorseContainer(horseId) != null)
            return false;

        horseContainers.add(new HorseContainer(ownerNick, horseId, horseContainerType, new ArrayList<>()));
        sync();
        return true;
    }

    public boolean remove(HorseContainer horseContainer)
    {
        if(!getHorseContainers().contains(horseContainer))
            return false;

        getHorseContainers().remove(horseContainer);
        config.set("containers." + horseContainer.getHorseId(), null);

        try { config.save(file); }
        catch (Exception exception) {}

        return true;
    }

    public HorseContainer getHorseContainer(UUID horseId)
    {
        HorseContainer horseContainer;
        try
        {
            horseContainer = getHorseContainers().stream().filter(value ->
                value.getHorseId() == horseId).findFirst().get();
        }
        catch (Exception exception) { return null; }

        return horseContainer;
    }

    public List<HorseContainer> getHorseContainers() { return horseContainers; }

    public void sync()
    {
        for(HorseContainer horseContainer : getHorseContainers())
        {
            ConfigurationSection section = config.getConfigurationSection("containers." + horseContainer.getHorseId());
            if(section == null)
            {
                config.createSection("containers." + horseContainer.getHorseId());

                try { config.save(file); }
                catch (Exception exception) {}

                section = config.getConfigurationSection("containers." + horseContainer.getHorseId());
            }

            section.set("owner_nick", horseContainer.getOwnerNick());
            section.set("container_type", horseContainer.getContainerType().toString());
            if(horseContainer.getItems() == null || horseContainer.getItems().isEmpty()) continue;

            int index = 0;
            for(ItemStack item : horseContainer.getItems())
            {
                index++;
                if(item == null) continue;

                section.set("items." + index, item);
            }
        }

        try { config.save(file); }
        catch (Exception exception) {}
    }
}
