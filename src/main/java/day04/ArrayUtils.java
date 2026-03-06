package day04;

public final class ArrayUtils {

    private ArrayUtils() {
    }

    public static int[] copy(int[] source) {
        if (source == null) {
            return null;
        }
        int[] result = new int[source.length];
        return copyArray(source, result);
    }

    public static int[] reverse(int[] source) {
        if (source == null) {
            return null;
        }
        int[] result = new int[source.length];
        return reverseArray(source, result);
    }

    public static boolean contains(int[] source, int value) {
        if (source == null) {
            return false;
        }
        return containsValue(source, value);
    }

    public static int indexOf(int[] source, int value) {
        if (source == null) {
            return -1;
        }
        return indexOfValue(source, value);
    }

    public static int safeGet(int[] source, int index, int defaultValue) {
        if (source == null) {
            return defaultValue;
        }
        if (index < 0 || index >= source.length) {
            return defaultValue;
        }
        return source[index];
    }

    private static int[] copyArray(int[] source, int[] result) {
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    private static int[] reverseArray(int[] source, int[] result) {
        for (int i = 0; i < source.length; i++) {
            int reverseIndex = source.length - 1 - i;
            result[reverseIndex] = source[i];
        }
        return result;
    }

    private static boolean containsValue(int[] source, int value) {
        for (int element : source) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    private static int indexOfValue(int[] source, int value) {
        for (int i = 0; i < source.length; i++) {
            if (source[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
