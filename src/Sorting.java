public class Sorting {

    public static void exchange(Comparable [] array, int i, int j) {
        Comparable swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static boolean isSorted(Comparable [] array) {

        for(int i= 0; i < array.length; i++) {
            if(less(array[i], array[i-1])) {
                return false;
            }
        }
        return true;

    }

}
