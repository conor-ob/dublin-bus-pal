package ie.dublinbuspal.android.data.local;

public final class DbInfo {

    private DbInfo() {
        throw new UnsupportedOperationException();
    }

    //Database
    public static final String DB_NAME = "dublinbusold.db";

    //Tables
    public static final String BUS_STOP_TABLE = "tb_bus_stops";
    public static final String BUS_STOP_ID = "stop_id";
    public static final String BUS_STOP_NAME = "stop_name";
    public static final String BUS_STOP_LATITUDE = "stop_latitude";
    public static final String BUS_STOP_LONGITUDE = "stop_longitude";

    public static final String ROUTES_TABLE = "tb_routes";
    public static final String ROUTE_ID = "route_id";
    public static final String ROUTE_ORIGIN = "route_origin";
    public static final String ROUTE_DESTINATION = "route_destination";

    public static final String BUS_STOP_SERVICE_TABLE = "tb_bus_stop_service";
    public static final String BUS_STOP_SERVICE_ID = "service_id";
    public static final String BUS_STOP_SERVICE_ROUTES = "service_routes";

    public static final String UNCHECKED_BUS_STOP_SERVICE_TABLE = "tb_unchecked_bus_stop_service";
    public static final String UNCHECKED_BUS_STOP_SERVICE_ID = "service_id";
    public static final String UNCHECKED_BUS_STOP_SERVICE_ROUTES = "service_routes";

    public static final String ROUTE_SERVICE_TABLE = "tb_route_service";
    public static final String ROUTE_SERVICE_ID = "service_id";
    public static final String ROUTE_SERVICE_NAME = "service_name";
    public static final String ROUTE_SERVICE_ORIGIN = "service_origin";
    public static final String ROUTE_SERVICE_DESTINATION = "service_destination";
    public static final String ROUTE_SERVICE_INBOUND_STOPS = "service_inbound_stops";
    public static final String ROUTE_SERVICE_OUTBOUND_STOPS = "service_outbound_stops";

    public static final String FAVOURITES_TABLE = "tb_favourites";
    public static final String FAVOURITE_ID = "favourite_id";
    public static final String FAVOURITE_NAME = "favourite_custom_name";
    public static final String FAVOURITE_ROUTES = "favourite_custom_routes";
    public static final String FAVOURITE_ORDER = "favourite_order";

    //Queries
    public static final String SELECT_BUS_STOPS = "SELECT * FROM tb_bus_stops";
    public static final String DELETE_BUS_STOPS = "DELETE FROM tb_bus_stops";

    public static final String SELECT_ROUTES = "SELECT * FROM tb_routes";
    public static final String DELETE_ROUTES = "DELETE FROM tb_routes";

    public static final String SELECT_BUS_STOP_SERVICES = "SELECT * FROM tb_bus_stop_service";
    public static final String SELECT_BUS_STOP_SERVICE = "SELECT * FROM tb_bus_stop_service " +
            "WHERE service_id=:stopId";
    public static final String DELETE_BUS_STOP_SERVICES = "DELETE FROM tb_bus_stop_service";

    public static final String SELECT_UNCHECKED_BUS_STOP_SERVICES = "SELECT * FROM " +
            "tb_unchecked_bus_stop_service";
    public static final String DELETE_UNCHECKED_BUS_STOP_SERVICES = "DELETE FROM " +
            "tb_unchecked_bus_stop_service";

    public static final String SELECT_ROUTE_SERVICE = "SELECT * FROM tb_route_service WHERE " +
            "service_id=:routeId";
    public static final String DELETE_ROUTE_SERVICES = "DELETE FROM tb_route_service";

    public static final String SELECT_FAVOURITE = "SELECT * FROM tb_favourites WHERE " +
            "favourite_id=:id ORDER BY favourite_order";
    public static final String COUNT_FAVOURITES = "SELECT count(*) FROM tb_favourites";

    public static final String SELECT_DETAILED_FAVOURITES = "SELECT tb_bus_stops.stop_id," +
            "tb_bus_stops.stop_name,tb_bus_stops.stop_latitude,tb_bus_stops.stop_longitude," +
            "tb_favourites.favourite_custom_routes AS routes,tb_favourites.favourite_custom_name " +
            "AS customName FROM tb_favourites LEFT JOIN tb_bus_stops ON " +
            "tb_favourites.favourite_id=tb_bus_stops.stop_id";

    public static final String SELECT_DETAILED_BUS_STOPS = "SELECT tb_bus_stops.stop_id," +
            "tb_bus_stops.stop_name,tb_bus_stops.stop_latitude,tb_bus_stops.stop_longitude," +
            "tb_bus_stop_service.service_routes AS routes," +
            "tb_unchecked_bus_stop_service.service_routes AS uncheckedRoutes," +
            "tb_favourites.favourite_custom_routes AS customRoutes," +
            "tb_favourites.favourite_custom_name AS customName FROM tb_bus_stops LEFT JOIN " +
            "tb_bus_stop_service ON tb_bus_stops.stop_id=tb_bus_stop_service.service_id LEFT " +
            "JOIN tb_unchecked_bus_stop_service ON tb_bus_stops.stop_id=" +
            "tb_unchecked_bus_stop_service.service_id LEFT JOIN tb_favourites ON " +
            "tb_bus_stops.stop_id=tb_favourites.favourite_id";

}
