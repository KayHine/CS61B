import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted a Queue of unsorted items
     * @param pivot the item to pivot on
     * @param less an empty Queue. When the function completes, this queue will contain
     *             all of the items in unsorted that are less than the given pivot.
     * @param equal an empty Queue. When the function completes, this queue will contain
     *              all of the items in unsorted that are equal to the given pivot.
     * @param greater an empty Queue. When the function completes, this queue will contain
     *                all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot, Queue<Item> less,
            Queue<Item> equal, Queue<Item> greater) {

        for (Item i : unsorted) {
            if (i.compareTo(pivot) > 0) {
                greater.enqueue(i);
            } else if (i.compareTo(pivot) < 0) {
                less.enqueue(i);
            } else {
                equal.enqueue(i);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {

        if (items.size() <= 1) {
            return items;
        }

        Item pivot = getRandomItem(items);
        Queue<Item> less = new Queue<Item>();
        Queue<Item> equal = new Queue<Item>();
        Queue<Item> greater = new Queue<Item>();

        partition(items, pivot, less, equal, greater);

        // passing in less/greater by reference doesn't change the original so have to
        // reassign back to less/greater
        less = quickSort(less);
        greater = quickSort(greater);

        Queue<Item> first = catenate(less, equal);
        return catenate(first, greater);
    }

    public static void main(String[] args) {

        Queue<Integer> students = new Queue<>();
        students.enqueue(10);
        students.enqueue(5);
        students.enqueue(1);
        students.enqueue(3);
        students.enqueue(15);
        students.enqueue(7);

        System.out.println(students);

        Queue<Integer> sortedQ = QuickSort.quickSort(students);
        System.out.println(sortedQ);
    }
}
