package ie.dublinbuspal.domain.usecase.rss

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import ie.dublinbuspal.domain.mapping.rss.RssMapper
import ie.dublinbuspal.domain.model.rss.NewsItem
import ie.dublinbuspal.domain.repository.rss.RssRepository
import ie.dublinbuspal.service.MockDublinBusRssApi
import ie.dublinbuspal.service.model.rss.RssResponseXml
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class RssUseCaseTest {

    private val useCase by lazy {

        val api = MockDublinBusRssApi()

        val memoryPolicy = MemoryPolicy.builder()
                .setExpireAfterWrite(24)
                .setExpireAfterTimeUnit(TimeUnit.HOURS)
                .build()

        val mapper = RssMapper()
        val store = StoreBuilder.parsedWithKey<Any, RssResponseXml, List<NewsItem>>()
                .fetcher { api.getRssNews() }
                .parser { xml -> mapper.map(xml.channel!!.newsItems) }
                .memoryPolicy(memoryPolicy)
                .refreshOnStale()
                .open()

        val repository = RssRepository(store)

        return@lazy RssUseCase(repository)
    }

    @Test
    fun testGetRss() {
        useCase.getRss()
                .doOnNext { print(it.toString()) }
                .doOnError { Assert.fail() }
                .subscribe()
    }

}