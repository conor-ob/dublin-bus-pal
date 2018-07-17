package ie.dublinbuspal.base.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public final class CollectionUtils {

    private CollectionUtils() {}

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> T safeFirstElement(List<T> collection) {
        if (isNullOrEmpty(collection)) {
            return null;
        }
        Iterator<T> iterator = collection.iterator();
        return iterator.next();
    }

    public static <T> T safeLastElement(Collection<T> collection) {
        if (isNullOrEmpty(collection)) {
            return null;
        }
        Iterator<T> iterator = collection.iterator();
        T next = null;
        while (iterator.hasNext()) {
            next = iterator.next();
        }
        return next;
    }

    public static <T> Set<T> toSet(Collection<T> collection) {
        Set<T> set = new HashSet<>();
        if (isNullOrEmpty(collection)) {
            return set;
        }
        set.addAll(collection);
        return set;
    }

    public static <K, V> SortedMap<K, V> headMap(SortedMap<K, V> map, int limit) {
        int count = 0;
        TreeMap<K,V> headMap = new TreeMap<>();
        for (Map.Entry<K,V> entry : map.entrySet()) {
            if (count >= limit) {
                break;
            }
            headMap.put(entry.getKey(), entry.getValue());
            count++;
        }
        return headMap;
    }

}
