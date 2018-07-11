package ie.dublinbuspal.database.migration;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.migration.Migration;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ie.dublinbuspal.database.DublinBusDatabase;

@RunWith(AndroidJUnit4.class)
public abstract class MigrationTest {

    private static final String TEST_DB = "migration-test";
    private final int fromVersion;
    private final int toVersion;
    private final Migration migration;

    @Rule
    public MigrationTestHelper helper;

    public MigrationTest(int fromVersion, int toVersion, Migration migration) {
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
        this.migration = migration;
        this.helper = new MigrationTestHelper(
                InstrumentationRegistry.getInstrumentation(),
                DublinBusDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory()
        );
    }

    @Test
    public void testMigration() throws IOException {
        SupportSQLiteDatabase database = helper.createDatabase(TEST_DB, fromVersion);

        assertDatabaseIntegrityPreMigration(database);

        populateDatabase(database);
        database.close();

        database = helper.runMigrationsAndValidate(TEST_DB, toVersion, true, migration);

        assertDatabaseIntegrityPostMigration(database);
        database.close();
    }

    protected abstract void assertDatabaseIntegrityPreMigration(SupportSQLiteDatabase database);

    protected abstract void populateDatabase(SupportSQLiteDatabase database);

    protected abstract void assertDatabaseIntegrityPostMigration(SupportSQLiteDatabase database);

}
