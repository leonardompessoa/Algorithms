public class InsertionSort {

    public static void sort(Comparable[] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (Sorting.less(array[j], array[j-1])) {
                    Sorting.exchange(array, j, j-1);
                } else {
                    break;
                }
            }

        }
    }

    public static void main(String[] args) {

        String[] array = new String[]{"b", "d", "a", "c"};
        InsertionSort.sort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

    }

}
