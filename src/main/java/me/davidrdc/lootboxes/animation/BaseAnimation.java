package me.davidrdc.lootboxes.animation;

import me.davidrdc.lootboxes.box.Box;
import me.davidrdc.lootboxes.reward.RewardTier;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseAnimation implements Animation {
  private Box box;
  private RewardTier tier;
  private Runnable callback;

  @Override
  public void play(JavaPlugin plugin, Box box, RewardTier tier, Runnable callback) {
    this.box = box;
    this.tier = tier;
    this.callback = callback;
  }

  @Override
  public Box getBox() {
    return box;
  }

  @Override
  public RewardTier getTier() {
    return tier;
  }

  @Override
  public Runnable getCallback() {
    return callback;
  }
}
