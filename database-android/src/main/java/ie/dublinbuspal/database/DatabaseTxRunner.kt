package ie.dublinbuspal.database

import ie.dublinbuspal.base.TxRunner

class DatabaseTxRunner(private val database: DublinBusDatabase) : TxRunner {

    override fun runInTx(task: () -> Unit) = database.runInTransaction(task)

}
