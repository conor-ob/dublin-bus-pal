package ie.dublinbuspal.database

class DatabaseTxRunner(private val database: DublinBusDatabase) : TxRunner {

    override fun runInTx(task: () -> Unit) = database.runInTransaction(task)

}
