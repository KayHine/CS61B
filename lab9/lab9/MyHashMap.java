package lab9;

import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INIT_CAPACITY = 4;
    private static final double INIT_LOAD_FACTOR = 10;

    private int size;
    private int pairs;
    private double loadFactor;      // total entries / number of buckets
    private LinkedList<Entry>[] bins;

    public MyHashMap() {
        this(INIT_CAPACITY);
        this.loadFactor = INIT_LOAD_FACTOR;
    }

    public MyHashMap(int initialSize) {
        this.size = initialSize;
        this.loadFactor = INIT_LOAD_FACTOR;
        bins = (LinkedList<Entry>[]) new LinkedList[size];
        for (int i = 0; i < size; i++) {
            bins[i] = new LinkedList<Entry>();
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.size = initialSize;
        this.loadFactor = loadFactor;
        bins = (LinkedList<Entry>[]) new LinkedList[size];
        for (int i = 0; i < size; i++) {
            bins[i] = new LinkedList<Entry>();
        }
    }

    private void resize(int newSize) {
        MyHashMap<K, V> temp = new MyHashMap<>(newSize);
        for (int i = 0; i < size; i++) {
            for (Entry ent : bins[i]) {
                temp.put((K) ent.key, getListValue(bins[i], (K) ent.key));
            }
        }
        this.size = temp.size;
        this.bins = temp.bins;
    }

    // hash balue between 0 and 1
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % size;
    }

    @Override
    public void clear() {
        this.size = INIT_CAPACITY;
        this.pairs = 0;
        // re-initializing bins means you have to reinitalize each LinkedList too
        bins = (LinkedList<Entry>[]) new LinkedList[size];
        for (int i = 0; i < size; i++) {
            bins[i] = new LinkedList<Entry>();
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int i = hash(key);
        return getListValue(bins[i], key);
    }

    @Override
    public int size() {
        return pairs;
    }


    @Override
    public void put(K key, V value) {
        if (pairs / size > loadFactor) resize(2 * size);

        int i = hash(key);
        if (getListValue(bins[i], key) == null) {
            Entry newEntry = new Entry(key, value);
            bins[i].add(newEntry);
            pairs++;
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (int i = 0; i < size; i++) {
            for (Entry ent: bins[i]) {
                keys.add((K) ent.key);
            }
        }
        return keys;
    }

    @Override
    public Iterator<K> iterator() {
        Set<K> keys = keySet();
        return keys.iterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public V getListValue(List<Entry> list, K key) {
        Iterator it = list.iterator();
        V val = null;
        while (it.hasNext()) {
            Entry current = (Entry) it.next();
            // need to use equals() to compare the values of current.key and key
            // == compared the reference of the two objects and so it won't work
            if (current.getKey().equals(key)) {
                val = (V) current.value;
            }
        }
        return val;
    }

    private class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }
    }

}