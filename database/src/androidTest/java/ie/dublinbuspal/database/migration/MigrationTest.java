package ie.dublinbuspal.database.migration;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.io.IOException;

import ie.dublinbuspal.database.DublinBusDatabase;

@RunWith(AndroidJUnit4.class)
public abstract class MigrationTest {

    private static final String TEST_DATABASE_NAME = "test.db";

    @Rule
    public MigrationTestHelper helper = new MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            DublinBusDatabase.class.getCanonicalName(),
            new FrameworkSQLiteOpenHelperFactory()
    );

    protected void testMigration(int fromVersion, int toVersion, Migration migration) throws IOException {
        SupportSQLiteDatabase database = helper.createDatabase(TEST_DATABASE_NAME, fromVersion);
        populateDatabasePreMigration(database);
        database.close();
        database = helper.runMigrationsAndValidate(TEST_DATABASE_NAME, toVersion, true, migration);
        database.close();
        assertDatabaseIntegrityPostMigration(getMigratedDatabase());
    }

    private DublinBusDatabase getMigratedDatabase() {
        DublinBusDatabase database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                DublinBusDatabase.class, TEST_DATABASE_NAME)
                .addMigrations(getMigrations())
                .build();
        helper.closeWhenFinished(database);
        return database;
    }

    protected abstract Migration[] getMigrations();

    protected abstract void populateDatabasePreMigration(SupportSQLiteDatabase database);

    protected abstract void assertDatabaseIntegrityPostMigration(DublinBusDatabase database);

}
