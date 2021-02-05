package com.github.dennermelo.jpescaria.enums;

import com.github.dennermelo.jpescaria.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

public enum Settings {

    ENABLE_MCMMO("Sistemas.mcMMO"),
    BREAK_ROD("Vara.Quebrar");

    private final String path;
    @Getter @Setter public boolean value;

    Settings(String path) {
        this.path = path;
    }

    public static void loadSettings() {
        FileConfiguration config = Main.plugin.getConfig();
        for (Settings value : Settings.values()) {
            value.setValue(config.getBoolean(value.path));
        }
    }

}