package me.davidrdc.lootboxes.reward;

import org.bukkit.Material;
import org.bukkit.Sound;

public enum RewardTier {
  COMMON("§7Common", Material.WOOD_SWORD, Sound.VILLAGER_IDLE),
  RARE("§9Rare", Material.STONE_SWORD, Sound.VILLAGER_YES),
  EPIC("§5Epic", Material.DIAMOND_SWORD, Sound.WOLF_GROWL),
  LEGENDARY("§6Legendary", Material.GOLD_SWORD, Sound.ENDERDRAGON_GROWL);

  private String displayName;
  private Material material;
  private Sound sound;

  RewardTier(String displayName, Material material, Sound sound) {
    this.displayName = displayName;
    this.material = material;
    this.sound = sound;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Material getMaterial() {
    return material;
  }

  public Sound getSound() {
    return sound;
  }
}
