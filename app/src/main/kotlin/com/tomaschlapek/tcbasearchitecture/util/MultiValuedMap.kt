package com.tomaschlapek.tcbasearchitecture.util

class MultiValuedMap<K, V> {

  private val map: MutableMap<K, MutableList<V>>

  init {
    this.map = HashMap()
  }

  fun put(key: K, value: V) {
    if (map.containsKey(key)) {
      map[key]?.add(value)
    } else {
      val values = ArrayList<V>()
      values.add(value)
      map.put(key, values)
    }
  }

  fun putValues(key: K, values: List<V>) {
    for (i in values.indices) {
      put(key, values[i])
    }
  }

  fun getValues(key: K): List<V>? {
    return if (map.containsKey(key)) {
      map[key]
    } else null
  }

  fun removeValues(key: K, values: List<V>) {
    for (i in values.indices) {
      remove(key, values[i])
    }
  }

  fun remove(key: K, value: V) {
    if (map.containsKey(key)) {
      map[key]?.remove(value)
    }
  }

  override fun toString(): String {
    var s = ""
    for ((key, value) in map) {
      for (i in 0..value.size - 1) {
        s += key.toString() + " " + value.toString()
      }
    }
    return s
  }
}