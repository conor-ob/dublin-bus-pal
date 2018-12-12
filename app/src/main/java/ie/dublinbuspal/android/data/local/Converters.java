package ie.dublinbuspal.android.data.local;

import androidx.room.TypeConverter;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.util.StringUtilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Converters {

    private final static String DELIMETER = "::";

    private Converters() {
        throw new UnsupportedOperationException();
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        if (CollectionUtilities.isNullOrEmpty(list)) {
            return null;
        }
        return StringUtilities.join(list, DELIMETER);
    }

    @TypeConverter
    public static List<String> toList(String string) {
        if (string != null) {
            return Arrays.asList(string.split(DELIMETER));
        }
        return Collections.emptyList();
    }

}
