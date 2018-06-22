package ie.dublinbuspal.android.data;

class DublinBusRepositoryLocks {

    private final Object busStopsLock = new Object();
    private final Object routesLock = new Object();

    DublinBusRepositoryLocks() {
        super();
    }

    Object getBusStopsLock() {
        return busStopsLock;
    }

    Object getRoutesLock() {
        return routesLock;
    }

}
