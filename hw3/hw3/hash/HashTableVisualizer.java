package hw3.hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static hw3.hash.HashTableDrawingUtility.xCoord;
import static hw3.hash.HashTableDrawingUtility.yCoord;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        double scale = 0.5;
        int N = 2000;
        int M = 100;

        HashTableDrawingUtility.setScale(scale);
        Set<Oomage> oomies = new HashSet<Oomage>();
        for (int i = 0; i < N; i += 1) {
            oomies.add(ComplexOomage.randomComplexOomage());
        }
        visualize(oomies, M, scale);
    }

    public static void visualize(Set<Oomage> set, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);

        HashMap<Integer, Integer> buckets = new HashMap<>();
        for (int i = 0; i < M; i++) {
            buckets.put(i, 0);
        }

        Iterator it = set.iterator();
        while (it.hasNext()) {
            Oomage om = (Oomage) it.next();
            int bucket = (om.hashCode() & 0x7fffffff) % M;
            int count = buckets.get(bucket);
            om.draw(xCoord(count), yCoord(bucket, M), scale);
            buckets.put(bucket, count + 1);
        }

        /* When done with visualizer, be sure to try 
           scale = 0.5, N = 2000, M = 100. */           
    }
} 
