package com.github.dennermelo.jpescaria.events;

import com.github.dennermelo.jpescaria.Main;
import com.github.dennermelo.jpescaria.enums.Messages;
import com.github.dennermelo.jpescaria.enums.Settings;
import com.github.dennermelo.jpescaria.objects.Item;
import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.util.Misc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerFishingEvent implements Listener {

    @EventHandler
    public void event(PlayerFishEvent event) {

        if (event.getState() != null) {
            return;
        }

        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) ||
                event.getState().equals(PlayerFishEvent.State.FISHING) ||
                event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT) ||
                event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)) {
            if (!event.getState().equals(PlayerFishEvent.State.IN_GROUND)) {
                for (Item item : Main.itemManager.getItemList()) {
                    int dropProbability = Misc.getRandom().nextInt(100);

                    if (dropProbability < item.getChance()) {
                        if (event.getPlayer().getInventory().contains(item.getItem())) {
                            if (verifyItems(event.getPlayer(), item.getItem())) {
                                event.getPlayer().getInventory().addItem(item.getItem());
                            } else if (verifySlots(event.getPlayer(), 1)) {
                                event.getPlayer().getInventory().addItem(item.getItem());
                            } else {
                                event.getPlayer().sendMessage(Messages.INVENTORY_SPACE_ENOUGH.getMessage());
                                return;
                            }
                        } else {
                            int i = 1;
                            if (verifySlots(event.getPlayer(), i)) {
                                event.getPlayer().getInventory().addItem(item.getItem());
                            } else {
                                event.getPlayer().sendMessage(Messages.INVENTORY_SPACE_ENOUGH.getMessage());
                                return;
                            }
                        }
                    }
                    event.getPlayer().setLevel(event.getPlayer().getLevel() + item.getXp());
                    if (!Settings.BREAK_ROD.getValue()) {
                        event.getPlayer().getItemInHand().setDurability((short) 0);
                    }
                    if (Settings.ENABLE_MCMMO.value && Bukkit.getPluginManager().isPluginEnabled("mcMMO")) {
                        ExperienceAPI.addLevel(event.getPlayer(), "Fishing", item.getMcmmoxp());
                    }
                }
            }
        }
    }

    public boolean verifySlots(Player player, int required) {
        int i = 0;
        int maxStack = 0;
        for (ItemStack item : player.getInventory()) {
            if (item == null) {
                i++;
            }
        }
        return i >= required;
    }

    public boolean verifyItems(Player player, ItemStack itemStack) {
        int maxStack = 0;
        for (ItemStack item : player.getInventory()) {
            if (item != null && item.isSimilar(itemStack) && item.getMaxStackSize() != 1) {
                maxStack = maxStack + (64 - item.getAmount());
            }
        }
        return maxStack >= itemStack.getAmount();
    }
}
