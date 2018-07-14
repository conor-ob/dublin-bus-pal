package ie.dublinbuspal.base

interface TxRunner {

    fun runInTx(task: () -> Unit)

}
