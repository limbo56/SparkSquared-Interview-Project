package me.davidrdc.lootboxes.util;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * An extension of the {@link BukkitRunnable} class that adds a callback that is called when the
 * runnable ends
 */
public abstract class BetterRunnable extends BukkitRunnable {
  private Runnable onEnd;

  @Override
  public synchronized void cancel() throws IllegalStateException {
    super.cancel();
    if (onEnd != null) onEnd.run();
  }

  public BetterRunnable onEnd(Runnable onEnd) {
    this.onEnd = onEnd;
    return this;
  }
}
