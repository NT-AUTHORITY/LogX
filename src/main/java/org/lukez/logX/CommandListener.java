package org.lukez.logX;

import org.bukkit.Location;
import org.bukkit.World;
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
            BlockCommandSender cmdBlock = (BlockCommandSender) event.getSender();
            Location loc = cmdBlock.getBlock().getLocation();
            World world = loc.getWorld();
            String worldName = LogX.getWorldAbbreviation(world.getName());
            String command = event.getCommand();

            String logMessage = String.format("[CB] [%s] %d,%d,%d: %s",
                    worldName,
                    loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                    command
            );
            LogX.getPluginLogger().info(logMessage);
        }
    }
}