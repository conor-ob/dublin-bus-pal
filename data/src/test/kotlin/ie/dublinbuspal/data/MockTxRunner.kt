package ie.dublinbuspal.data

import ie.dublinbuspal.data.TxRunner

class MockTxRunner : TxRunner {

    override fun runInTx(task: () -> Unit) {
        task.invoke()
    }

}
