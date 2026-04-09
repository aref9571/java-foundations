package day12;

import java.util.List;

public final class CollectionUtils {

    private CollectionUtils(){}

    public static int totalSize(List<? extends CharSequence> items){
        if (items == null){
            return 0;
        }
        int total = 0;
        for (CharSequence item : items){
            if (item != null){
                total += item.length();
            }
        }
        return total;
    }

    public static <T> T firstOrNull(List<T> items){
        if (items == null || items.isEmpty()){
            return null;
        }
        return items.getFirst();
    }

    public static <T> boolean containsNull(List<T> items){
        if (items == null){
            return false;
        }
        for (T item: items){
            if (item == null){
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> copy(List<T> items){
        if (items == null){
            return List.of();
        }
        return List.copyOf(items);
    }

}
