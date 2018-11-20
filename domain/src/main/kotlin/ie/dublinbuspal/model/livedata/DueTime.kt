package ie.dublinbuspal.model.livedata

data class DueTime(
        val minutes: Long,
        val time: String
) {

    fun minutes(): String {
        return when {
            minutes < 0L -> "Late"
            minutes == 0L -> "Due"
            minutes == 1L -> "$minutes min"
            else -> "$minutes mins"
        }
    }

}
