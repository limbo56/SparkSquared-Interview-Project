package me.davidrdc.lootboxes.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class Hologram {
  private static final double SEPARATOR = 0.4;

  private Location location;
  private Map<Integer, String> lines;
  private List<ArmorStand> entities;

  public Hologram(Location location) {
    this.location = location;
    this.lines = new HashMap<>();
    this.entities = new ArrayList<>();
  }

  public Hologram show() {
    lines.keySet().forEach(this::spawnLine);
    return this;
  }

  public Hologram hide() {
    entities.forEach(Entity::remove);
    entities.clear();
    return this;
  }

  public Hologram setLine(int line, String text) {
    if (!lines.containsKey(line)) {
      lines.put(line, text);
      spawnLine(line);
      return this;
    }

    removeLine(line);
    lines.put(line, text);
    spawnLine(line);
    return this;
  }

  public Hologram removeLine(int line) {
    if (!lines.containsKey(line)) return this;
    entities.stream()
        .filter(entity -> entity.getCustomName().equalsIgnoreCase(lines.remove(line)))
        .findFirst()
        .ifPresent(
            entity -> {
              entity.remove();
              entities.remove(entity);
            });
    return this;
  }

  private void spawnLine(int line) {
    spawnHologram(location.clone().subtract(0, line * SEPARATOR, 0), lines.get(line));
  }

  private void spawnHologram(Location location, String text) {
    ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
    armorStand.setGravity(false);
    armorStand.setVisible(false);
    armorStand.setCustomName(text);
    armorStand.setCustomNameVisible(true);
    armorStand.setSmall(true);
    entities.add(armorStand);
  }

  public Location getLocation() {
    return location;
  }
}
