package org.lukez.logX;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerActionListener implements Listener {
    private final LogX plugin;

    public PlayerActionListener(LogX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        World world = player.getWorld();
        String worldName = world.getName();
        worldName = LogX.getWorldAbbreviation(worldName);
        player.getLocation();
        String logMessage = String.format("[B] %s [%s] %d,%d,%d: Broke %s",
                player.getName(),
                worldName,
                loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                event.getBlock().getType().toString()
        );
        LogX.getPluginLogger().info(logMessage);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        World world = player.getWorld();
        String worldName = world.getName();
        worldName = LogX.getWorldAbbreviation(worldName);
        String logMessage = String.format("[P] %s [%s] %d,%d,%d: Placed %s",
                player.getName(),
                worldName,
                loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                event.getBlock().getType().toString()
        );
        LogX.getPluginLogger().info(logMessage);
    }
}