package org.lukez.logX;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.command.BlockCommandSender;

public class CommandListener implements Listener {
    private final LogX plugin;

    public CommandListener(LogX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        World world = player.getWorld();
        String worldName = world.getName();
        worldName = LogX.getWorldAbbreviation(worldName);
        String command = event.getMessage();
        String logMessage = String.format("[C] %s [%s] %d,%d,%d: %s",
                player.getName(),
                worldName,
                loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                command
        );
        LogX.getPluginLogger().info(logMessage);
    }

    @EventHandler
    public void onCommandBlockCommand(ServerCommandEvent event) {
        if (event.getSender() instanceof BlockCommandSender) {
            BlockCommandSender sender = (BlockCommandSender) event.getSender();
            Block block = sender.getBlock();

            if (block.getState() instanceof CommandBlock) {
                CommandBlock cmdBlock = (CommandBlock) block.getState();
                Location loc = cmdBlock.getLocation();
                World world = loc.getWorld();
                String worldName = LogX.getWorldAbbreviation(world.getName());
                String command = event.getCommand();

                // 获取命令方块类型
                String blockType = getCommandBlockType(block.getType());

                // 获取触发类型
                // 没有 API，用不了
                // String triggerType = cmdBlock.doesRequirePower() ? "R" : "A";
                String triggerType = "_"; // Placeholder...

                String logMessage = String.format("[CB] [%s] [%s] [%s] %d,%d,%d: %s",
                        blockType,
                        triggerType,
                        worldName,
                        loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                        command
                );
                LogX.getPluginLogger().info(logMessage);
            }
        }
    }

    private String getCommandBlockType(Material material) {
        switch (material) {
            case COMMAND_BLOCK:
                return "I";
            case CHAIN_COMMAND_BLOCK:
                return "C";
            case REPEATING_COMMAND_BLOCK:
                return "R";
            default:
                return "?";
        }
    }
}