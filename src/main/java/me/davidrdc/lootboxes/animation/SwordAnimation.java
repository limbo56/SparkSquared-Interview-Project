package me.davidrdc.lootboxes.animation;

import me.davidrdc.lootboxes.box.Box;
import me.davidrdc.lootboxes.reward.RewardTier;
import me.davidrdc.lootboxes.util.BetterRunnable;
import me.davidrdc.lootboxes.util.Utils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class SwordAnimation extends BaseAnimation {
  // Animation config
  private static final double INTERVAL = Math.PI * 2 / 16;
  private static final double RADIUS = 2;
  private static final double SEPARATOR = 3.5;
  private static final double RADIUS_DECREASE_RATE = 0.02;

  private Location center;
  private int tick;
  private double radius = RADIUS;
  private ArmorStand sword1;
  private ArmorStand sword2;

  @Override
  public void play(JavaPlugin plugin, Box box, RewardTier tier, Runnable callback) {
    super.play(plugin, box, tier, callback);
    this.center = box.getLocation();
    getSwordHelixTask(plugin).runTaskTimer(plugin, 0L, 1L);
  }

  private BetterRunnable getSwordHelixTask(JavaPlugin plugin) {
    return new BetterRunnable() {
      @Override
      public void run() {
        double angle = tick * INTERVAL;

        if (sword1 == null || sword2 == null) {
          sword1 = spawnStand(angle);
          sword2 = spawnStand(angle + SEPARATOR);
          Utils.playSound(center, Sound.HORSE_SADDLE, 1f, 1f);
        }

        moveSwords(angle);
        radius -= RADIUS_DECREASE_RATE;

        if (radius <= 0) {
          sword2.remove();
          this.cancel();
        }

        ++tick;
      }

      private void moveSwords(double angle) {
        Location location = getLocationInCircle(angle, radius);
        location.setY(center.getY() + 0.03 * tick);
        location.setYaw(getLookAtYaw(center.toVector(), location.toVector()));

        Location location2 = getLocationInCircle(angle + SEPARATOR, radius);
        location2.setY(center.getY() + 0.03 * tick);
        location2.setYaw(getLookAtYaw(center.toVector(), location2.toVector()));

        sword1.teleport(location.clone().setDirection(center.toVector()).add(0.38, 0, 0.5));
        sword2.teleport(location2.clone().setDirection(center.toVector()).add(0.38, 0, 0.5));
        center.getWorld().playEffect(location.clone().add(0, 0.25, 0), Effect.HAPPY_VILLAGER, 0);
        center.getWorld().playEffect(location2.clone().add(0, 0.25, 0), Effect.HAPPY_VILLAGER, 0);
      }
    }.onEnd(() -> getInsertTask().runTaskTimer(plugin, 0L, 1L));
  }

  private BetterRunnable getInsertTask() {
    int steps = 50;
    Vector increment =
        new Vector(0, center.getY() - sword1.getLocation().getY(), 0)
            .divide(new Vector(steps, steps, steps));

    return new BetterRunnable() {
      private int currentStep;

      @Override
      public void run() {
        if (currentStep++ >= steps) cancel();
        Location particleLocation =
            sword1.getLocation().clone().subtract(0.38, 0, 0.5).add(0, 2, 0).add(increment);

        // Move sword and spawn particles
        sword1.teleport(sword1.getLocation().add(increment));
        center.getWorld().playEffect(particleLocation, Effect.HAPPY_VILLAGER, 0);
      }
    }.onEnd(
        () -> {
          Utils.playSound(center, Sound.CHEST_OPEN, 1f, 5f);
          Utils.runTaskLater(
              () -> {
                sword1.remove();
                center.getWorld().playEffect(center, Effect.EXPLOSION_LARGE, 0);
                Utils.playSound(center.clone().add(0, 1, 0), Sound.FIREWORK_LARGE_BLAST, 1f, 1f);
                getCallback().run();
              },
              40L);
        });
  }

  private ArmorStand spawnStand(double angle) {
    Location locationInCircle = getLocationInCircle(angle, RADIUS);
    locationInCircle.add(0.38, 0, 0.5); // Center right arm
    ArmorStand stand = locationInCircle.getWorld().spawn(locationInCircle, ArmorStand.class);

    stand.setArms(false);
    stand.setBasePlate(false);
    stand.setVisible(false);
    stand.setGravity(false);
    stand.setItemInHand(new ItemStack(getTier().getMaterial()));
    stand.setRightArmPose(new EulerAngle(Math.toRadians(80), 0, 0));

    return stand;
  }

  private float getLookAtYaw(Vector target, Vector source) {
    Vector direction = target.subtract(source).normalize();
    return 180 - (float) Math.toDegrees(Math.atan2(direction.getX(), direction.getZ()));
  }

  private Location getLocationInCircle(double angle, double radius) {
    double x = center.getX() + radius * Math.cos(angle);
    double z = center.getZ() + radius * Math.sin(angle);
    return new Location(center.getWorld(), x, center.getY(), z, 0, 0);
  }
}
