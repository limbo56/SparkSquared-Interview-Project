package me.davidrdc.lootboxes.box.menu;

import java.util.List;
import me.davidrdc.lootboxes.LootBoxes;
import me.davidrdc.lootboxes.box.Box;
import me.davidrdc.lootboxes.keys.Key;
import me.davidrdc.lootboxes.keys.KeyManager;
import me.davidrdc.lootboxes.util.menu.MenuHolder;
import me.davidrdc.lootboxes.util.menu.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BoxMenu extends MenuHolder {
  private Box box;

  public BoxMenu(Box box) {
    super("§8Loot Boxes", 45);
    this.box = box;
  }

  public static MenuItem getKeyItem(Key key) {
    return new MenuItem(Material.CHEST)
        .name("§eLoot Box")
        .lore(
            "",
            "§7Loot boxes contain a variety of ",
            "§7random cosmetics and give you a ",
            "§7based on your luck",
            "",
            "§7Rarity: " + key.getType().getDisplayName(),
            "§7Multiplier: §f" + key.getType().getMultiplier(),
            "");
  }

  @Override
  public void openMenu(Player player) {
    addItems(player);
    super.openMenu(player);
  }

  private void addItems(Player player) {
    KeyManager keyManager = LootBoxes.getInstance().getKeyManager();
    List<Key> keys = keyManager.getKeys(player);

    if (keys.size() == 0) {
      setItem(
          22,
          new MenuItem(Material.STAINED_GLASS_PANE)
              .name("§cYou don't have any keys")
              .lore("", "§7Use /givekey <type> to give yourself", "§7a key", "")
              .durability((short) 14)
              .amount(1)
              .action(() -> {}));
      return;
    }

    for (int i = 0; i < Math.min(keys.size(), 25); i++) {
      Key key = keys.get(i);
      MenuItem keyItem = getKeyItem(key);
      int slot = 10 + i;

      if (slot % 9 == 0) {
        slot += 1;
      } else if ((slot + 1) % 9 == 0) {
        slot += 2;
      }

      setItem(slot, keyItem.action(() -> new ConfirmationMenu(box, key).openMenu(player)));
    }
  }
}
