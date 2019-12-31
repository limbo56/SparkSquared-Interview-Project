package me.davidrdc.lootboxes.commands;

import java.util.Arrays;
import me.davidrdc.lootboxes.LootBoxes;
import me.davidrdc.lootboxes.keys.Key;
import me.davidrdc.lootboxes.keys.KeyManager;
import me.davidrdc.lootboxes.keys.KeyType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveKeyCommand implements CommandExecutor {
  private LootBoxes plugin;

  public GiveKeyCommand(LootBoxes plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return false;
    }

    if (args.length < 1) {
      sender.sendMessage("Usage: /giveKey <type>");
      return false;
    }

    String experimentalType = args[0].toUpperCase();

    if (Arrays.stream(KeyType.values()).noneMatch(type -> type.name().equals(experimentalType))) {
      sender.sendMessage("§cInvalid type! Available types: common, rare, epic, and legendary");
      return false;
    }

    KeyType type = KeyType.valueOf(args[0].toUpperCase());
    KeyManager keyManager = plugin.getKeyManager();
    keyManager.addKey(((Player) sender).getUniqueId(), new Key(type));
    sender.sendMessage("§aYou have been granted a " + type.getDisplayName() + " §akey");
    return true;
  }
}
