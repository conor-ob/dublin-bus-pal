package ie.dublinbuspal.database

interface TxRunner {

    fun runInTx(task: () -> Unit)

}
