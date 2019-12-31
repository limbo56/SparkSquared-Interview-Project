package me.davidrdc.lootboxes.util;

import me.davidrdc.lootboxes.LootBoxes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

public class Utils {

  public static void runTaskLater(Runnable task, long delay) {
    Bukkit.getScheduler().runTaskLater(LootBoxes.getInstance(), task, delay);
  }

  public static void playSound(Location location, Sound sound, float volume, float pitch) {
    location.getWorld().playSound(location, sound, volume, pitch);
  }
}
