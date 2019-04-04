package ie.dublinbuspal.mapping.livedata

//class DefaultLiveDataEntityMapper : Mapper<RealTimeStopDataDataXml, RealTimeStopData> {
//
//    override fun map(from: RealTimeStopDataDataXml): RealTimeStopData {
//        return RealTimeStopData(from.routeId!!, mapDestination(from.destination!!), mapDueTime(from.timestamp!!, from.expectedTimestamp!!))
//    }
//
//    private fun mapDestination(destination: String): Destination {
//        return Destination(getDestination(destination), getVia(destination))
//    }
//
//    private fun mapDueTime(timestamp: String, expectedTime: String): DueTime {
//        val timestampInstant = TimeUtils.dateTimeStampToInstant(timestamp, Formatter.isoDateTime)
//        val expectedTimeInstant = TimeUtils.dateTimeStampToInstant(expectedTime, Formatter.isoDateTime)
//        val minutes = TimeUtils.minutesBetween(timestampInstant, expectedTimeInstant)
//        return DueTime(minutes, TimeUtils.formatAsTime(expectedTimeInstant))
//    }
//
//    //TODO duplicate code
//    private fun getDestination(destination: String): String {
//        if (destination.contains("via")) {
//            val i = destination.indexOf("via")
//            return destination.substring(0, i).trim { it <= ' ' }
//        }
//        return destination
//    }
//
//    private fun getVia(destination: String): String? {
//        if (destination.contains("via")) {
//            val i = destination.indexOf("via")
//            return destination.substring(i, destination.length).trim { it <= ' ' }
//        }
//        return null
//    }
//
//}
