package com.github.dennermelo.jpescaria.enums;

import com.github.dennermelo.jpescaria.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

public enum Messages {


    INVENTORY_SPACE_ENOUGH("Inventario.sem-espaco");

    private final String path;
    @Getter
    @Setter
    public String message;

    Messages(String path) {
        this.path = path;
    }

    public static void loadMessages() {
        FileConfiguration config = Main.plugin.getConfig();
        for (Messages message : Messages.values()) {
            message.setMessage(config.getString("Mensagens." + message.path).replace("&", "§"));
        }
    }


}
