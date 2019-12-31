package me.davidrdc.lootboxes.listeners;

import me.davidrdc.lootboxes.LootBoxes;
import me.davidrdc.lootboxes.box.BoxManager;
import me.davidrdc.lootboxes.box.menu.BoxMenu;
import me.davidrdc.lootboxes.util.menu.MenuHolder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class LootBoxListener implements Listener {
  private LootBoxes plugin;

  public LootBoxListener(LootBoxes plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onLootBoxInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Block block = event.getClickedBlock();

    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    if (block.getType() != Material.CHEST) {
      return;
    }

    if (!block.hasMetadata("LootBox")) {
      return;
    }

    BoxManager boxManager = plugin.getBoxManager();

    event.setCancelled(true);
    boxManager
        .getByLocation(block.getLocation())
        .ifPresent(
            box -> {
              if (box.isInUse()) {
                player.sendMessage("§cSomeone else is already using this loot box");
                return;
              }
              new BoxMenu(box).openMenu(player);
            });
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();

    if (block.getType() != Material.CHEST) {
      return;
    }

    if (!block.hasMetadata("LootBox")) {
      return;
    }

    event.setCancelled(true);
    event.getPlayer().sendMessage("§cYou can't break loot boxes");
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    InventoryHolder holder = event.getClickedInventory().getHolder();
    int slot = event.getSlot();

    if (!(holder instanceof MenuHolder)) {
      return;
    }

    MenuHolder menuHolder = (MenuHolder) holder;

    if (!menuHolder.getItemList().containsKey(slot)) {
      return;
    }

    event.setCancelled(true);
    menuHolder.getItemList().get(slot).getClickAction().run();
  }
}
