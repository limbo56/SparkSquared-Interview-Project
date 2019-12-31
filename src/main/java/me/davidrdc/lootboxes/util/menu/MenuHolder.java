package me.davidrdc.lootboxes.util.menu;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {
  private String title;
  private int size;
  private Map<Integer, MenuItem> itemList;

  public MenuHolder(String title, int size) {
    this.title = title;
    this.size = size;
    this.itemList = new HashMap<>();
  }

  public void openMenu(Player player) {
    Inventory inventory = getInventory();
    itemList.forEach((slot, item) -> inventory.setItem(slot, item.getItemStack()));
    player.openInventory(inventory);
  }

  protected void setItem(int slot, MenuItem item) {
    itemList.put(slot, item);
  }

  @Override
  public Inventory getInventory() {
    return Bukkit.createInventory(this, size, title);
  }

  public String getTitle() {
    return title;
  }

  public int getSize() {
    return size;
  }

  public Map<Integer, MenuItem> getItemList() {
    return itemList;
  }
}
