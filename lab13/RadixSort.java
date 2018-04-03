/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra
 * @version 1.4 - April 14, 2016
 *
 **/
public class RadixSort
{

    /**
     * Does Radix sort on the passed in array with the following restrictions:
     *  The array can only have ASCII Strings (sequence of 1 byte characters)
     *  The sorting is stable and non-destructive
     *  The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     **/
    public static String[] sort(String[] asciis)
    {
        String[] sorted = new String[asciis.length];
        System.arraycopy(asciis, 0, sorted, 0, asciis.length);
        sortHelper(sorted, 0, sorted.length, 0);
        return sorted;
    }

    /**
     * Radix sort helper function that recursively calls itself to achieve the sorted array
     *  destructive method that changes the passed in array, asciis
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelper(String[] asciis, int start, int end, int index)
    {
        if (end <= start + 1) return;
        int[] count = new int[256];
        String[] temp = new String[asciis.length];
        // count frequencies
        for (int i = 0; i < asciis.length; i++) {
            count[asciis[i].charAt(index) + 1] += 1;
        }
        // computer cumulates
        for (int k = 1; k < 256; k++) {
            count[k] += count[k -1];
        }
        // move records
        for (int i = 0; i < asciis.length; i++) {
            temp[count[asciis[i].charAt(index)]++] = asciis[i];
        }
        // copy back
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = temp[i];
        }
        for (int i = 1; i < 255; i++) {
            sortHelper(asciis, start + count[i], start + count[i + 1], index + 1);
        }
    }

    public static void main(String[] args) {
        String[] ascii = {"apple", "ape", "banana", "bear", "zebra", "crocodile"};
        String[] sorted = sort(ascii);
        for (int i = 0; i < sorted.length; i++) {
            System.out.print(sorted[i] + " ");
        }
    }
}
