package me.davidrdc.lootboxes.keys;

public class Key implements Comparable<Key> {
  private static int COUNT = 0;

  private int id;
  private KeyType type;

  public Key(KeyType type) {
    this.id = COUNT++;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public KeyType getType() {
    return type;
  }

  @Override
  public int compareTo(Key key2) {
    return Integer.compare(key2.id, this.id);
  }
}
