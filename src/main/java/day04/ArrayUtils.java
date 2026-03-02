package day04;

public final class ArrayUtils {
    private ArrayUtils(){}

    public static int[] copy(int[] source){
        if (source == null){
            return null;
        }
        int [] result = new int[source.length];
        for (int i = 0 ; i < source.length ; i++){
            result[i] = source[i];
        }
        return result;
    }
    public static int[] reverse(int[] source){
        if (source == null) {
            return null;
        }
        int [] result = new int[source.length];
        for (int i = 0 ; i < source.length ; i++){
            int reverseIndex = source.length - 1 - i;
            result[reverseIndex] = source[i];
        }
        return result;
    }

    public static boolean contains(int[] source , int value){
        if (source == null) {
            return false;
        }
        for (int i : source) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }
    public static int indexOf(int[] source , int value){
        if (source == null) {
            return -1;
        }
        for (int i = 0 ; i < source.length ; i++){
            if (source[i] == value){
                return i;
            }
        }
        return -1;
    }
    public static int safeGet(int[] source , int index , int defaultValue){
        if (source == null) {
            return defaultValue;
        }
        if (index < 0 || index >= source.length ){
            return defaultValue;
        }
        return source[index];

    }
}
