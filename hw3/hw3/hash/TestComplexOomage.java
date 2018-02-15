package hw3.hash;

import edu.princeton.cs.algs4.Complex;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

import edu.princeton.cs.algs4.StdRandom;


public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    public boolean haveNiceHashCodeSpread(Set<ComplexOomage> oomages) {
        /* TODO: Write a utility function that ensures that the oomages have
         * hashCodes that would distribute them fairly evenly across
         * buckets To do this, mod each's hashCode by M = 10,
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        boolean fewer = true;
        boolean more = true;

        HashMap<Integer, Integer> buckets = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            buckets.put(i, 0);
        }

        for (ComplexOomage om : oomages) {
            int bucket = om.hashCode() % 10;
            int count = buckets.get(bucket);
            buckets.put(bucket, count + 1);
        }

        for (Integer k : buckets.values()) {
            if (k < oomages.size() / 50) fewer = false;
            if (k > oomages.size() / 2.5) more = false;
        }

        return fewer && more;
    }


    @Test
    public void testRandomItemsHashCodeSpread() {
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    @Test
    public void testWithDeadlyParams() {
        /* TODO: Create a Set that shows the flaw in the hashCode function.
         */
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
