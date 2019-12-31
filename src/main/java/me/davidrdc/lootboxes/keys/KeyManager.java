package me.davidrdc.lootboxes.keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

public class KeyManager {
  private Map<UUID, List<Key>> keys;

  public KeyManager() {
    this.keys = new HashMap<>();
  }

  public void addPlayer(Player player) {
    keys.putIfAbsent(player.getUniqueId(), new ArrayList<>());
  }

  public void removePlayer(Player player) {
    keys.remove(player.getUniqueId());
  }

  public void addKey(UUID uuid, Key key) {
    keys.get(uuid).add(key);
  }

  public void removeKey(UUID uuid, Key key) {
    keys.get(uuid).remove(key);
  }

  public List<Key> getKeys(Player player) {
    return keys.get(player.getUniqueId());
  }

  public Map<UUID, List<Key>> getKeys() {
    return keys;
  }
}
