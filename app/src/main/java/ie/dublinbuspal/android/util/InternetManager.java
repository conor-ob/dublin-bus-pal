package ie.dublinbuspal.android.util;

import static ie.dublinbuspal.android.util.ConnectionUtilities.Connection;

import android.content.Context;

public class InternetManager {

    private final Context context;

    public InternetManager(Context context) {
        this.context = context;
    }

    public boolean hasGoodConnection() {
        return Connection.GOOD.equals(getConnection());
    }

    public boolean hasOkConnection() {
        Connection connection = getConnection();
        return Connection.GOOD.equals(connection) || Connection.OK.equals(connection);
    }

    private Connection getConnection() {
        Object connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return ConnectionUtilities.getConnection(connectivityManager);
    }

}
