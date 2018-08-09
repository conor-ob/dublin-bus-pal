package ie.dublinbuspal.database

import ie.dublinbuspal.database.TxRunner

class MockTxRunner : TxRunner {

    override fun runInTx(task: () -> Unit) {
        task.invoke()
    }

}
