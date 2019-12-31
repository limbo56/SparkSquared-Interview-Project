package me.davidrdc.lootboxes.box;

import me.davidrdc.lootboxes.util.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Box {
  private Location location;
  private Hologram hologram;
  private boolean inUse;

  public Box(Location location) {
    this.location = location;
    this.location.setX(location.getBlockX() + 0.5);
    this.location.setZ(location.getBlockZ() + 0.5);
  }

  public void spawn(JavaPlugin plugin) {
    Block block = location.getBlock();
    block.setType(Material.CHEST);
    block.setMetadata("LootBox", new FixedMetadataValue(plugin, true));

    this.hologram =
        new Hologram(location.clone().add(0, 0.5, 0))
            .setLine(0, "§e§lLoot Box")
            .setLine(1, "§fRight click to open");
  }

  public Hologram getHologram() {
    return hologram;
  }

  public Location getLocation() {
    return location;
  }

  public boolean isInUse() {
    return inUse;
  }

  public void setInUse(boolean inUse) {
    this.inUse = inUse;
  }
}
