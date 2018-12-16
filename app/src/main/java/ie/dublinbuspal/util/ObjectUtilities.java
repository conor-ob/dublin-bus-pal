package ie.dublinbuspal.util;

public final class ObjectUtilities {

    private ObjectUtilities() {
        throw new UnsupportedOperationException();
    }

    public static <T> boolean safeEquals(T obj1, T obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

}
