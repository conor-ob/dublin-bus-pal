package ie.dublinbuspal.android.view.realtime;

import ie.dublinbuspal.android.data.local.entity.RealTimeData;

class EmptyRealTimeData extends RealTimeData {

    EmptyRealTimeData() {
        this("", "", "", "");
    }

    EmptyRealTimeData(String route, String destination, String expectedTime, String timestamp) {
        super(route, destination, expectedTime, timestamp);
    }

}
