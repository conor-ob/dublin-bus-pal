package ie.dublinbuspal.util

object StringUtils {

    const val EMPTY_STRING = ""
    const val AMPERSAND = "&"
    const val MIDDLE_DOT = "\u00B7"

    fun isNullOrEmpty(string: String?): Boolean {
        return string == null || EMPTY_STRING == string.trim { it <= ' ' }
    }

    fun join(strings: List<String>, delimeter: String): String {
        val stringBuilder = StringBuilder()
        for (i in strings.indices) {
            stringBuilder.append(strings[i])
            if (i < strings.size - 1) {
                stringBuilder.append(delimeter)
            }
        }
        return stringBuilder.toString()
    }

}