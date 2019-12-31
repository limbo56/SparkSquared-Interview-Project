package me.davidrdc.lootboxes.listeners;

import me.davidrdc.lootboxes.LootBoxes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
  private LootBoxes plugin;

  public PlayerListener(LootBoxes plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    plugin.getKeyManager().addPlayer(event.getPlayer());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    plugin.getKeyManager().removePlayer(event.getPlayer());
  }
}
