package ie.dublinbuspal.android.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class CollectionUtilities {

    private CollectionUtilities() {}

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

}
