package ie.dublinbuspal.domain.usecase.rss

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import ie.dublinbuspal.domain.mapping.rss.RssMapper
import ie.dublinbuspal.domain.model.rss.RssNews
import ie.dublinbuspal.domain.repository.rss.RssNewsRepository
import ie.dublinbuspal.service.MockDublinBusRssApi
import ie.dublinbuspal.service.model.rss.RssResponseXml
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