package ie.dublinbuspal.di

import dagger.Module
import dagger.Provides
import ie.dublinbuspal.service.*
import javax.inject.Singleton

@Module
class MockNetworkModule {

    @Provides
    @Singleton
    fun dublinBusSoapApi(): DublinBusSoapApi = MockDublinBusSoapApi()

    @Provides
    @Singleton
    fun dublinBusRssApi(): DublinBusRssApi = MockDublinBusRssApi()

    @Provides
    @Singleton
    fun smartDublinRestApi(): SmartDublinRestApi = MockSmartDublinBusRestApi()

}
