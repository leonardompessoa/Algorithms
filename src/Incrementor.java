public class Incrementor {

    public static void main(String[] args) {
        resultAsString(increment(new int [] {1, 2, 3}));
        resultAsString(increment(new int [] {9, 9, 9}));
        resultAsString(increment(new int [] {9, 0, 9}));
        resultAsString(increment(new int [] {5}));
        resultAsString(increment(new int [] {}));
    }

    private static void resultAsString(int[] incremented) {
        for(int i : incremented) {
            System.out.print(i);
            System.out.print("");
        }
        System.out.println("");
    }

    public static int[] increment(int[] number) {

        if(number.length <= 0) {
            return new int[] {1};
        }

        for(int i = number.length - 1; i >= 0; i--) {
            if(number[i] < 9) {
                number[i] = number[i] + 1;
                break;
            } else {
                number[i] = 0;
            }
        }
        if(number[0] == 0) {
            int [] incremented = new int [number.length + 1];
            incremented[0] = 1;
            return incremented;
        }
        return number;
    }



}
