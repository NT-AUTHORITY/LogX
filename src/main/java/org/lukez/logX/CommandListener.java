package org.lukez.logX;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

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
}