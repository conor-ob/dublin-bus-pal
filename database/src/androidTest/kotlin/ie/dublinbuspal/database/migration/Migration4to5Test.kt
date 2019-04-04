package ie.dublinbuspal.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ie.dublinbuspal.database.DublinBusDatabase
import org.junit.Test

class Migration4to5Test : MigrationTest() {

    @Test
    fun testMigration4to5() {
        testMigration(4, 5, Migrations.MIGRATION_4_5)
    }

    override fun getMigrations(): Array<Migration> {
        return arrayOf(Migrations.MIGRATION_1_2, Migrations.MIGRATION_3_4, Migrations.MIGRATION_4_5)
    }

    override fun populateDatabasePreMigration(database: SupportSQLiteDatabase?) {

    }

    override fun assertDatabaseIntegrityPostMigration(database: DublinBusDatabase?) {

    }

}
