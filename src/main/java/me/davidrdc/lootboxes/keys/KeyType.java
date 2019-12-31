package me.davidrdc.lootboxes.keys;

import me.davidrdc.lootboxes.animation.Animation;
import me.davidrdc.lootboxes.animation.SwordAnimation;

public enum KeyType {
  COMMON("§fCommon", 1.0),
  RARE("§9Rare", 1.2),
  EPIC("§5Epic", 1.5),
  LEGENDARY("§6Legendary", 2.0);

  private String displayName;
  private double multiplier;
  private Class<? extends Animation> animation;

  KeyType(String displayName, double multiplier) {
    this(displayName, multiplier, SwordAnimation.class);
  }

  KeyType(String displayName, double multiplier, Class<? extends Animation> animation) {
    this.displayName = displayName;
    this.multiplier = multiplier;
    this.animation = animation;
  }

  public String getDisplayName() {
    return displayName;
  }

  public double getMultiplier() {
    return multiplier;
  }

  public Animation getAnimation() {
    try {
      return animation.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      return new SwordAnimation();
    }
  }
}
