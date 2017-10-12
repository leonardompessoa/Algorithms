public class StringReversion {

    public static void main(String[] args) {
        StringReversion stringReversion = new StringReversion();
        System.out.println(stringReversion.sequentialReversion(args[0]));
        System.out.println(stringReversion.stackReversion(args[0]));
    }

    public String sequentialReversion(String word) {
        char [] sequence = word.toCharArray();
        char [] reverted = new char[sequence.length];
        int j = 0;
        for(int i = sequence.length-1; i >= 0; i--) {
            reverted[j] = sequence[i];
            j++;
        }
        return String.valueOf(reverted);
    }

    public String stackReversion(String word) {
        char [] sequence = word.toCharArray();
        char [] reverted = new char[sequence.length];
        int n = sequence.length;
        for(int i = 0; i < reverted.length; i++) {
            reverted[i] = sequence[--n];
        }
        return String.valueOf(reverted);
    }

}
