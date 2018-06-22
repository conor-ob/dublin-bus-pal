package ie.dublinbuspal.android.util;

import ie.dublinbuspal.android.data.local.entity.RealTimeData;

import java.util.Locale;

public final class RealTimeUtilities {

    private static final String VIA = "via";
    private static final String DUE = "Due";
    private static final String LATE = "Late";

    private RealTimeUtilities() {
        throw new UnsupportedOperationException();
    }

    public static String getDestination(RealTimeData realTimeData) {
        String destination = realTimeData.getDestination();
        if (destination.contains(VIA)) {
            int i = destination.indexOf(VIA);
            return destination.substring(0, i).trim();
        }
        return destination;
    }

    public static String getVia(RealTimeData realTimeData) {
        String destination = realTimeData.getDestination();
        if (destination.contains(VIA)) {
            int i = destination.indexOf(VIA);
            return destination.substring(i, destination.length()).trim();
        }
        return StringUtilities.EMPTY_STRING;
    }

    public static String get24HourTime(RealTimeData realTimeData) {
        long minutes = DateUtilities.getDifferenceInMinutes(realTimeData.getTimestamp(),
                realTimeData.getExpectedTime());
        if (minutes < 0) {
            return LATE;
        } else if (minutes < 1) {
            return DUE;
        }
        return DateUtilities.get24HourTime(realTimeData.getExpectedTime());
    }

    public static String getDueTime(RealTimeData realTimeData) {
        long minutes = DateUtilities.getDifferenceInMinutes(realTimeData.getTimestamp(),
                realTimeData.getExpectedTime());
        if (minutes < 0) {
            return LATE;
        } else if (minutes < 1) {
            return DUE;
        }
        return String.format(Locale.UK, "%s min", String.valueOf(minutes));
    }

}
