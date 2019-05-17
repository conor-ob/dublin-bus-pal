package ie.dublinbuspal.database.migration

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ie.dublinbuspal.database.DublinBusDatabase
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class MigrationTest {

    private val testDatabaseName = "test.db"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        DublinBusDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    protected fun testMigration(fromVersion: Int, toVersion: Int, migration: Migration) {
        var database = helper.createDatabase(testDatabaseName, fromVersion)
        populateDatabasePreMigration(database)
        database.close()
        database = helper.runMigrationsAndValidate(testDatabaseName, toVersion, true, migration)
        database.close()
        assertDatabaseIntegrityPostMigration(getMigratedDatabase())
    }

    private fun getMigratedDatabase(): DublinBusDatabase {
        val database = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            DublinBusDatabase::class.java, testDatabaseName
        )
            .fallbackToDestructiveMigrationFrom(1, 2)
            .addMigrations(
                Migrations.MIGRATION_3_4,
                Migrations.MIGRATION_4_5
            )
            .build()
        helper.closeWhenFinished(database)
        return database
    }

    protected abstract fun populateDatabasePreMigration(database: SupportSQLiteDatabase)

    protected abstract fun assertDatabaseIntegrityPostMigration(database: DublinBusDatabase)

}