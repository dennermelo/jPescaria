package com.github.dennermelo.jpescaria.objects;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class Item {

    @Getter
    private final String name;
    @Getter
    private final ItemStack item;
    @Getter
    private final int chance, xp, mcmmoxp;

    public Item(String name, ItemStack item, int chance, int xp, int mcmmoxp) {
        this.name = name;
        this.item = item;
        this.chance = chance;
        this.xp = xp;
        this.mcmmoxp = mcmmoxp;
    }
}
