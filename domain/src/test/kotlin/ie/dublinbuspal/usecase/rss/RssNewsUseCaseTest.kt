package ie.dublinbuspal.usecase.rss

import ie.dublinbuspal.model.rss.RssNews
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class RssNewsUseCaseTest {

    private val useCase by lazy {

        val api = MockDublinBusRssApi()

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val mapper = RssMapper()
        val store = StoreBuilder.parsedWithKey<Any, RssResponseXml, List<RssNews>>()
                .fetcher { api.getRssNews() }
                .parser { xml -> mapper.map(xml.channel!!.newsItems) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        val repository = RssNewsRepository(store)

        return@lazy RssNewsUseCase(repository)
    }

    @Test
    fun testGetRss() {
        useCase.getRssNews()
                .doOnNext { print(it.toString()) }
                .doOnError { Assert.fail() }
                .subscribe()
    }

}