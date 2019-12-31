package me.davidrdc.lootboxes.animation;

import me.davidrdc.lootboxes.box.Box;
import me.davidrdc.lootboxes.reward.RewardTier;
import org.bukkit.plugin.java.JavaPlugin;

public interface Animation {

  /**
   * Plays the animation
   *
   * @param plugin where the animation is running
   * @param box to play animation at
   * @param tier of the reward
   * @param callback to call when animation finishes
   */
  void play(JavaPlugin plugin, Box box, RewardTier tier, Runnable callback);

  /** @return {@link Box} where the animation is playing */
  Box getBox();

  /**
   * An animation may be customized depending on the reward's tier
   *
   * @return {@link RewardTier} of the reward
   */
  RewardTier getTier();

  /** @return {@link Runnable} callback called whenever the animation finishes */
  Runnable getCallback();
}
