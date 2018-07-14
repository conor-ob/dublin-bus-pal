package ie.dublinbuspal.base

class MockTxRunner : TxRunner {

    override fun runInTx(task: () -> Unit) {
        task.invoke()
    }

}
