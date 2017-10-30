public class SelectionSort {

    public static void sort(Comparable[] array) {

        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length - 1; j++) {
                if (Sorting.less(array[j], array[min])) {
                    min = j;
                }
            }
            if (min != i) {
                Sorting.exchange(array, i, min);
            }
        }
    }

    public static void main(String[] args) {

        String[] array = new String[]{"b", "d", "a", "c"};
        SelectionSort.sort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

    }


}
