package ie.dublinbuspal.database.migration

import androidx.sqlite.db.SupportSQLiteDatabase
import ie.dublinbuspal.data.entity.FavouriteStopEntity
import ie.dublinbuspal.database.DublinBusDatabase
import io.reactivex.observers.TestObserver
import org.junit.Test

class Migration1to2Test : MigrationTest() {

    @Test
    fun testMigration1to2() {
        testMigration(1, 2, Migrations.MIGRATION_1_2)
    }

    override fun populateDatabasePreMigration(database: SupportSQLiteDatabase) {
        database.execSQL("INSERT INTO tb_bus_stop_service VALUES('123', '1A::2B::3C')")
        database.execSQL("INSERT INTO tb_bus_stops VALUES('123', 'Bus Stop', 53.9411, -5.815)")
        database.execSQL("INSERT INTO tb_favourites VALUES('123', 'My Bus Stop', '1A::2B', 0)")
        database.close()
    }

    override fun assertDatabaseIntegrityPostMigration(database: DublinBusDatabase) {
        val testObserver = TestObserver<List<FavouriteStopEntity>>()
        database.favouriteStopDao().selectAll().toObservable().subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertResult(emptyList())
    }

}
