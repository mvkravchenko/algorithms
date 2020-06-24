package com.example.maps;

import java.util.Map;

public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {
    // average number of entries per map before we rehash
    protected static final double FACTOR = 1.0;

    @Override
    public V put(K key, V value) {
        V oldValue = super.put(key, value);

        //System.out.println("Put " + key + " in " + map + " size now " + map.size());

        // check if the number of elements per map exceeds the threshold
        if (size() > maps.size() * FACTOR) {
            rehash();
        }
        return oldValue;
    }

    /**
     * Doubles the number of maps and rehashes the existing entries.
     */
    /**
     *
     */
    protected void rehash() {
        // TODO: FILL THIS IN!
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Integer> map = new MyHashMap<String, Integer>();
        for (int i=0; i<10; i++) {
            map.put(new Integer(i).toString(), i);
        }
        Integer value = map.get("3");
        System.out.println(value);
    }
}
