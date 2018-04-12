package com.sudoplay.mc.pwcustom.modules.veins.util;

import java.util.Objects;

public class LongObjectMap<V> {

  private long[] key;
  private Object[] value;
  private int size;

  public LongObjectMap(int capacity) {

    this.key = new long[capacity];
    this.value = new Object[capacity];
  }

  public int size() {

    return this.size;
  }

  public boolean containsKey(long k) {

    return this.findKey(k) != -1;
  }

  public boolean containsValue(Object v) {

    for (int i = this.size; i-- != 0; ) {

      if (Objects.equals(this.value[i], v)) {
        return true;
      }
    }

    return false;
  }

  public V get(long k) {

    long[] key = this.key;

    for (int i = this.size; i-- != 0; ) {

      if (key[i] == k) {
        //noinspection unchecked
        return (V) this.value[i];
      }
    }

    return null;
  }

  public boolean isEmpty() {

    return this.size == 0;
  }

  public V put(long k, V v) {

    int oldKey = this.findKey(k);

    if (oldKey != -1) {
      //noinspection unchecked
      V oldValue = (V) this.value[oldKey];
      this.value[oldKey] = v;
      return oldValue;
    }

    if (this.size == this.key.length) {
      long[] newKey = new long[this.size == 0 ? 2 : this.size * 2];
      Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];

      for (int i = this.size; i-- != 0; ) {
        newKey[i] = this.key[i];
        newValue[i] = this.value[i];
      }
      this.key = newKey;
      this.value = newValue;
    }

    this.key[this.size] = k;
    this.value[this.size] = v;
    this.size += 1;
    return null;
  }

  public V remove(long k) {

    int oldPos = this.findKey(k);

    if (oldPos == -1) {
      return null;
    }
    //noinspection unchecked
    V oldValue = (V) this.value[oldPos];
    int tail = this.size - oldPos - 1;
    System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
    System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
    this.size -= 1;
    this.value[this.size] = null;
    return oldValue;
  }

  public long[] keys() {

    return this.key;
  }

  public Object[] values() {

    return this.value;
  }

  private int findKey(long k) {

    long[] key = this.key;

    for (int i = this.size; i-- != 0; ) {

      if (key[i] == k) {
        return i;
      }
    }

    return -1;
  }
}
