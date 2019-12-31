package me.davidrdc.lootboxes.util.menu;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem {
  private Runnable clickAction;
  private ItemStack itemStack;

  public MenuItem(Material material) {
    this.itemStack = new ItemStack(material);
  }

  public MenuItem(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  public MenuItem action(Runnable clickAction) {
    this.clickAction = clickAction;
    return this;
  }

  public MenuItem amount(int amount) {
    itemStack.setAmount(amount);
    return this;
  }

  public MenuItem durability(short durability) {
    itemStack.setDurability(durability);
    return this;
  }

  public MenuItem name(String name) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(name);
    itemStack.setItemMeta(itemMeta);
    return this;
  }

  public MenuItem lore(String... lore) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setLore(Arrays.asList(lore));
    itemStack.setItemMeta(itemMeta);
    return this;
  }

  public Runnable getClickAction() {
    return clickAction;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }
}
