package me.davidrdc.lootboxes.commands;

import me.davidrdc.lootboxes.LootBoxes;
import me.davidrdc.lootboxes.box.Box;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnLootBox implements CommandExecutor {
  private LootBoxes plugin;

  public SpawnLootBox(LootBoxes plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return false;
    }

    Box box = new Box(((Player) sender).getLocation());
    plugin.getBoxManager().addBox(box);
    box.spawn(plugin);
    sender.sendMessage("§aA loot box has been spawned");
    return true;
  }
}
