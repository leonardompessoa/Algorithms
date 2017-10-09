public class Searching {

    public static void main(String[] args) {

        char [] array = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        System.out.println("linear search: " + linearSearch(array, 'h'));
        System.out.println("linear search: " + linearSearch(array, '§'));
        System.out.println("Sentinel linear search: " + sentinelLinearSearch(array, 'h'));
        System.out.println("Sentinel linear search: " + sentinelLinearSearch(array, '§'));
        System.out.println("Binary search: " + recursiveBinarySearch(array,0, array.length - 1, 'h'));
        System.out.println("Binary search: " + recursiveBinarySearch(array,0, array.length - 1, '§'));

    }
    
    private static int linearSearch(char[] array, char search) {
        int position = -1;
        for(int i = 0; i < array.length; i++){
            if(search == array[i]){
                position = i;
                break;
            }
        }
        return position;
    }

    private static int sentinelLinearSearch(char[] array, char search) {
        int position = 0;
        int lastPosition = array.length-1;
        char lastData = array[lastPosition];
        array[lastPosition] = search;

        while (array[position] != search){
            position++;
        }
        array[lastPosition] = lastData;

        if(!(position < lastPosition || array[lastPosition] == search)) {
            position = -1;
        }

        return position;
    }

    private static int recursiveBinarySearch(char[] array, int init, int end, char search) {
        if(init > end) {
            return -1;
        }
        int middle = (init + end)/2;
        if(array[middle] == search) {
            return middle;
        } else {
            if(search > array[middle]){
                init = middle + 1;
            } else if(search < array[middle]) {
                end = middle - 1;
            }

            return recursiveBinarySearch(array, init, end, search);

        }
    }
}
