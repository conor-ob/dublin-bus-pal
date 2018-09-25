package ie.dublinbuspal.android

//import ie.dublinbuspal.android.di.DaggerMockApplicationComponent
import ie.dublinbuspal.android.di.ApplicationComponent
import ie.dublinbuspal.android.di.MockApplicationModule
import ie.dublinbuspal.database.di.InMemoryDatabaseModule
import ie.dublinbuspal.di.MockNetworkModule
import ie.dublinbuspal.di.RepositoryModule
import org.junit.BeforeClass
import org.junit.Test

class MockDublinBusApplication {

    private lateinit var applicationComponent: ApplicationComponent

    @BeforeClass
    fun setup() {
//        applicationComponent = DaggerMockApplicationComponent.builder().build()

    }

    @Test
    fun testTheThing() {
        val app = MockApplicationModule()
        val db = InMemoryDatabaseModule()
        val rep = RepositoryModule()
        val net = MockNetworkModule()
    }

}
