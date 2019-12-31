package me.davidrdc.lootboxes.box.menu;

import static me.davidrdc.lootboxes.box.menu.BoxMenu.getKeyItem;

import me.davidrdc.lootboxes.LootBoxes;
import me.davidrdc.lootboxes.box.Box;
import me.davidrdc.lootboxes.keys.Key;
import me.davidrdc.lootboxes.keys.KeyManager;
import me.davidrdc.lootboxes.reward.Reward;
import me.davidrdc.lootboxes.util.Utils;
import me.davidrdc.lootboxes.util.menu.MenuHolder;
import me.davidrdc.lootboxes.util.menu.MenuItem;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

public class ConfirmationMenu extends MenuHolder {
  private Box box;
  private Key key;

  public ConfirmationMenu(Box box, Key key) {
    super("§7Confirmation", 45);
    this.box = box;
    this.key = key;
  }

  @Override
  public void openMenu(Player player) {
    addItems(player);
    super.openMenu(player);
  }

  private void addItems(Player player) {
    setItem(13, getKeyItem(key));
    setItem(
        29,
        new MenuItem(new Wool(DyeColor.GREEN).toItemStack())
            .name("§aOpen")
            .amount(1)
            .action(() -> openLootBox(player)));
    setItem(
        33,
        new MenuItem(new Wool(DyeColor.RED).toItemStack())
            .name("§cCancel")
            .amount(1)
            .action(() -> player.getOpenInventory().close()));
  }

  private void openLootBox(Player player) {
    LootBoxes plugin = LootBoxes.getInstance();
    Reward reward = Reward.getRandomReward(key.getType().getMultiplier());
    KeyManager keyManager = plugin.getKeyManager();

    player.getOpenInventory().close();

    // Check if in use
    if (box.isInUse()) {
      player.sendMessage("§cSomeone else is already using this loot box");
      return;
    }

    // Remove key and set loot box to in use
    keyManager.removeKey(player.getUniqueId(), key);
    box.setInUse(true);

    // Hide hologram and play animation
    box.getHologram().hide();
    key.getType().getAnimation().play(plugin, box, reward.getTier(), () -> showReward(reward));
  }

  private void showReward(Reward reward) {
    // Play sound and display
    Utils.playSound(box.getLocation(), reward.getTier().getSound(), 1f, 1f);
    box.getHologram().setLine(0, reward.getTier().getDisplayName()).setLine(1, reward.getName());

    // Reset loot box
    Utils.runTaskLater(
        () -> {
          box.getHologram().setLine(0, "§e§lLoot Box").setLine(1, "§fRight click to open");
          box.setInUse(false);
        },
        100L);
  }
}
