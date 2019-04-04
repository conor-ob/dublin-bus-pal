package ie.dublinbuspal.util

enum class Operator(
        val displayName: String,
        val code: String
) {

    DUBLIN_BUS("Dublin Bus", "BAC"),
    GO_AHEAD("Go Ahead", "GAD");

    override fun toString() = displayName

    companion object {

        fun parse(value: String): Operator {
            for (operator in values()) {
                if (operator.displayName.equals(value, ignoreCase = true)
                        || operator.code.equals(value, ignoreCase = true)) {
                    return operator
                }
            }
            throw IllegalArgumentException("Unable to parse Operator from string value \"$value\"")
        }

    }

}
