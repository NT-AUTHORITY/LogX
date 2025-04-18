package org.lukez.logX;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class LogX extends JavaPlugin {
    private static Logger logger;
    private FileHandler fileHandler;

    @Override
    public void onEnable() {
        // 创建日志目录和文件
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        try {
            File logFile = new File(getDataFolder(), "player_actions.log");
            fileHandler = new FileHandler(logFile.getPath(), true);
            fileHandler.setFormatter(new SimpleFormatter());

            logger = Logger.getLogger(LogX.class.getName());
            logger.addHandler(fileHandler);

            // 注册事件监听器
            getServer().getPluginManager().registerEvents(new PlayerActionListener(this), this);
            getServer().getPluginManager().registerEvents(new CommandListener(this), this);

            getLogger().info("LogX 插件已启动！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (fileHandler != null) {
            fileHandler.close();
        }
        getLogger().info("LogX 插件已关闭！");
    }

    public static Logger getPluginLogger() {
        return logger;
    }

    // 映射世界名称到首字母缩写
    public static String getWorldAbbreviation(String worldName) {
        switch (worldName.toLowerCase()) {
            case "world":
                return "O"; // Overworld
            case "world_nether":
                return "N"; // Nether
            case "world_the_end":
                return "E"; // End
            default:
                return "?"; // 未知世界
        }
    }

}