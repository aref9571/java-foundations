package day02;

public final  class MathToolkit {
    private MathToolkit(){}

    public static int sum(int a , int b){
        return a+b;
    }

    public static int max(int a , int b , int c){
        int m = a;
        if (b > m) m = b;
        if (c > m) m = c;
        return m;
    }

    public static boolean isEven(int n){
        return n % 2 == 0;
    }

    public static int clamp(int value , int min , int max){
        if (min > max){
            throw new IllegalArgumentException("min must be <= max");
        }
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

}
