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
            getLogger().info(getDescription().getDescription());
            getLogger().info("版本：" + getDescription().getVersion());
            getLogger().info("==================================================");
            getLogger().info("注意：日志文件位于 " + logFile.getAbsolutePath());
            getLogger().info("==================================================");
            getLogger().info("LogX 插件使用说明：");
            getLogger().info("1. 插件会记录玩家的命令和方块操作（放置和破坏）到日志文件中。");
            getLogger().info("2. 日志文件格式如下：");
            getLogger().info("玩家破坏方块日志格式：");
            getLogger().info("[B] [玩家名] [维度] x,y,z: Broke 方块类型");
            getLogger().info("玩家放置方块日志格式：");
            getLogger().info("[P] [玩家名] [维度] x,y,z: Placed 方块类型");
            getLogger().info("玩家命令日志格式：");
            getLogger().info("[C] [玩家名] [维度] x,y,z: 命令");
            getLogger().info("命令方块日志格式：");
            getLogger().info("[CB] [方块类型] [触发类型] [维度] x,y,z: 命令");
            getLogger().info("命令方块日志现在不完善");
            getLogger().info("[CB] [方块类型] [触发类型] [维度] x,y,z: 命令");
            getLogger().info("中的 触发类型 一定为 _");
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