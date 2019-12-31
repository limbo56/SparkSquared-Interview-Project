package me.davidrdc.lootboxes;

import me.davidrdc.lootboxes.box.BoxManager;
import me.davidrdc.lootboxes.commands.GiveKeyCommand;
import me.davidrdc.lootboxes.commands.SpawnLootBox;
import me.davidrdc.lootboxes.keys.KeyManager;
import me.davidrdc.lootboxes.listeners.LootBoxListener;
import me.davidrdc.lootboxes.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LootBoxes extends JavaPlugin {
  private static LootBoxes instance;
  private KeyManager keyManager;
  private BoxManager boxManager;

  public static LootBoxes getInstance() {
    return instance;
  }

  public void onEnable() {
    instance = this;
    this.keyManager = new KeyManager();
    this.boxManager = new BoxManager();

    // Register commands
    Bukkit.getPluginCommand("spawnLootBox").setExecutor(new SpawnLootBox(this));
    Bukkit.getPluginCommand("giveKey").setExecutor(new GiveKeyCommand(this));

    // Register listeners
    Bukkit.getPluginManager().registerEvents(new LootBoxListener(this), this);
    Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
  }

  public void onDisable() {
    this.keyManager.getKeys().clear();
    this.boxManager.getBoxes().clear();
  }

  public KeyManager getKeyManager() {
    return keyManager;
  }

  public BoxManager getBoxManager() {
    return boxManager;
  }
}
