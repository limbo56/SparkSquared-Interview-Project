package me.davidrdc.lootboxes.reward;

import java.util.Arrays;
import java.util.Random;

public enum Reward {
  DRAGON_MOUNT(RewardTier.LEGENDARY, 5, "Dragon Mount"),
  WITHER_MOUNT(RewardTier.EPIC, 30, "Wither Mount"),
  OBSIDIAN_HAT(RewardTier.RARE, 60, "Obsidian Hat"),
  STONE_HAT(RewardTier.COMMON, 80, "Stone Hat");

  private static final Random random = new Random();
  private RewardTier tier;
  private double percentage;
  private String name;

  Reward(RewardTier tier, double percentage, String name) {
    this.tier = tier;
    this.percentage = percentage;
    this.name = name;
  }

  public static Reward getRandomReward(double multiplier) {
    double random = Reward.random.nextDouble() * getTotal();
    Reward choice = Reward.STONE_HAT;

    for (Reward reward : values()) {
      double percentage = reward.getPercentage() * multiplier;
      if (random <= percentage && percentage <= (choice.getPercentage() * multiplier)) {
        choice = reward;
      }
    }

    return choice;
  }

  private static double getTotal() {
    return Arrays.stream(values()).mapToDouble(reward -> reward.percentage).sum();
  }

  public RewardTier getTier() {
    return tier;
  }

  public double getPercentage() {
    return percentage;
  }

  public String getName() {
    return name;
  }
}
