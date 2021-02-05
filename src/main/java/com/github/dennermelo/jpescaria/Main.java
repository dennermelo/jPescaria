package com.github.dennermelo.jpescaria;

import com.github.dennermelo.jpescaria.enums.Messages;
import com.github.dennermelo.jpescaria.enums.Settings;
import com.github.dennermelo.jpescaria.events.PlayerFishingEvent;
import com.github.dennermelo.jpescaria.managers.ItemManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    public static Plugin plugin;
    @Getter
    public static ItemManager itemManager;

    @Override
    public void onEnable() {

        plugin = getPlugin(Main.class);

        /* Carregamento/Criação de itens */
        itemManager = new ItemManager();
        itemManager.loadItems();

        /* Carregamento de Mensagens/Configuracoes */
        Messages.loadMessages();
        Settings.loadSettings();

        /* Carregamento do Evento */
        Bukkit.getPluginManager().registerEvents(new PlayerFishingEvent(), plugin);

    }

    @Override
    public void onDisable() {
    }

}
