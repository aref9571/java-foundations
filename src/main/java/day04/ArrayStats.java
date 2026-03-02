package day04;

public final class ArrayStats {
    private ArrayStats(){}

    public static void validateNotEmpty (int[] values){
        if (values == null || values.length == 0){
            throw new IllegalArgumentException("values must not be empty");
        }
    }

    public static int min(int[] values){
        validateNotEmpty(values);
        int min = values[0];
        for (int i = 1 ; i < values.length ; i++ ){
            if (values[i] < min){
                min = values[i];
            }
        }
        return min;
    }

    public static int max(int[] values){
        validateNotEmpty(values);
        int max = values[0];
        for (int i = 1 ; i < values.length ; i++){
            if (values[i] > max){
                max = values[i];
            }
        }
        return max;
    }

    public static int sum(int[] values){
        validateNotEmpty(values);
        int sum = 0;
        for (int i : values){
            sum += i;
        }
        return sum;
    }

    public static double average(int[] values){
        validateNotEmpty(values);
        int sum = sum(values);
        return (double) sum / values.length;
    }
}
