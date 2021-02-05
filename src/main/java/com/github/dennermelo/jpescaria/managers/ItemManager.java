package com.github.dennermelo.jpescaria.managers;

import com.github.dennermelo.jpescaria.Main;
import com.github.dennermelo.jpescaria.objects.Item;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemManager {

    @Getter
    private final List<Item> itemList;

    public ItemManager() {
        itemList = new ArrayList<>();
    }

    public void loadItems() {
        FileConfiguration config = Main.plugin.getConfig();

        for (String key : config.getConfigurationSection("Itens").getKeys(false)) {
            String displayName = config.getString("Itens." + key + ".nome").replace("&", "§");
            String[] args = config.getString("Itens." + key + ".id").split("#");
            List<String> lore = config.getStringList("Itens." + key + ".descricao");
            Collections.replaceAll(lore, "&", "§");
            List<String> enchantments = config.getStringList("Itens." + key + ".encantamentos");
            int id = Integer.parseInt(args[0]);
            int data = Integer.parseInt(args[1]);
            int amount = config.getInt("Itens." + key + ".quantidade");

            ItemStack item = new ItemStack(Material.getMaterial(id), amount, (short) data);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            item.setItemMeta(meta);
            for (String enchantmentName : enchantments) {
                String[] args1 = enchantmentName.split(";");
                item.addUnsafeEnchantment(Enchantment.getByName(args1[0]), Integer.parseInt(args[1]));
            }
            int xp = config.getInt("Itens." + key + ".xp");
            int mcmmoxp = config.getInt("Itens." + key + ".mcmmo-xp");
            int chance = config.getInt("Itens." + key + ".chance");
            itemList.add(new Item(displayName, item, chance, xp, mcmmoxp));
        }
    }
}
