package ie.dublinbuspal.data

interface TxRunner {

    fun runInTx(task: () -> Unit)

}
