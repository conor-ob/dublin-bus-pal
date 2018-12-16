package ie.dublinbuspal.util;

import java.util.List;

public final class StringUtilities {

    public static final String EMPTY_STRING = "";
    public static final String AMPERSAND = "&";
    public static final String MIDDLE_DOT = "\u00B7";

    private StringUtilities() {
        throw new UnsupportedOperationException();
    }

    public static String join(List<String> strings, String delimeter) {
        StringBuilder routesBuilder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            routesBuilder.append(strings.get(i));
            if (i < strings.size() - 1) {
                routesBuilder.append(delimeter);
            }
        }
        return routesBuilder.toString();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || EMPTY_STRING.equals(string.trim());
    }

}
