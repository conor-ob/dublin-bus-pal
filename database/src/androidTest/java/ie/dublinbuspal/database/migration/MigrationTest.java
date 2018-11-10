package ie.dublinbuspal.database.migration;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.io.IOException;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
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
        DublinBusDatabase database = Room.databaseBuilder(ApplicationProvider.getApplicationContext(),
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
