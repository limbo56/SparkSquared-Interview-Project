package me.davidrdc.lootboxes.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.Location;

public class BoxManager {
  private List<Box> boxes;

  public BoxManager() {
    this.boxes = new ArrayList<>();
  }

  public void addBox(Box box) {
    boxes.add(box);
  }

  public Optional<Box> getByLocation(Location location) {
    return boxes.stream()
        .filter(box -> normalizeLocation(box.getLocation()).equals(normalizeLocation(location)))
        .findFirst();
  }

  private Location normalizeLocation(Location location) {
    return new Location(
        location.getWorld(),
        location.getBlockX() + 0.5,
        location.getBlockY(),
        location.getBlockZ() + 0.5);
  }

  public List<Box> getBoxes() {
    return boxes;
  }
}
