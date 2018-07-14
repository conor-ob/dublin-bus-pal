package ie.dublinbuspal.database.migration

import android.arch.persistence.db.SupportSQLiteDatabase

class Migration1to2Test : MigrationTest(1, 2, Migrations.MIGRATION_1_2) {

    override fun assertDatabaseIntegrityPreMigration(database: SupportSQLiteDatabase) {
    }

    override fun populateDatabase(database: SupportSQLiteDatabase) {
        database.execSQL("INSERT INTO tb_bus_stops VALUES('123', 'Bus Stop', 53.9411, -5.815)")
        database.execSQL("INSERT INTO tb_favourites VALUES('123', 'My Bus Stop', '1A::2B', 0)")

        // Prepare for the next version.
        database.close()
    }

    override fun assertDatabaseIntegrityPostMigration(database: SupportSQLiteDatabase) {
    }

}
