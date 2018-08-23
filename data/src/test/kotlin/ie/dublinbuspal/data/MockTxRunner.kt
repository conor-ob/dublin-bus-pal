package ie.dublinbuspal.data

class MockTxRunner : TxRunner {

    override fun runInTx(task: () -> Unit) {
        task.invoke()
    }

}
