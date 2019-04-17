package ie.dublinbuspal.di

import dagger.Module
import dagger.Provides
import ie.dublinbuspal.service.api.dublinbus.DublinBusApi
import ie.dublinbuspal.service.api.rss.RssApi
import ie.dublinbuspal.service.api.rtpi.RtpiApi
import ie.dublinbuspal.service.interceptor.DownloadProgressInterceptor
import ie.dublinbuspal.service.interceptor.NetworkLoggingInterceptor
import ie.dublinbuspal.service.resource.*
import ie.dublinbuspal.util.DownloadProgressListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(
        private val dublinBusApiEndpoint: String,
        private val rtpiApiEndpoint: String,
        private val rssApiEndpoint: String
) {

    private val callAdapter: CallAdapter.Factory by lazy { RxJava2CallAdapterFactory.create() }
    private val jsonDeserializer: Converter.Factory by lazy { GsonConverterFactory.create() }
    private val xmlDeserializer: Converter.Factory by lazy { SimpleXmlConverterFactory.create() }

    @Provides
    @Singleton
    fun okhttpClient(downloadProgressInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(downloadProgressInterceptor)
                .addNetworkInterceptor(NetworkLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun downloadProgressInterceptor(
            downloadProgressListener: DownloadProgressListener
    ): Interceptor {
        return DownloadProgressInterceptor(downloadProgressListener)
    }

    @Provides
    @Singleton
    fun dublinBusApi(okHttpClient: OkHttpClient): DublinBusApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(dublinBusApiEndpoint)
                .client(okHttpClient)
                .addConverterFactory(xmlDeserializer)
                .addCallAdapterFactory(callAdapter)
                .build()
        return retrofit.create(DublinBusApi::class.java)
    }

    @Provides
    @Singleton
    fun rtpiApi(okHttpClient: OkHttpClient): RtpiApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(rtpiApiEndpoint)
                .client(okHttpClient)
                .addConverterFactory(jsonDeserializer)
                .addCallAdapterFactory(callAdapter)
                .build()
        return retrofit.create(RtpiApi::class.java)
    }

    @Provides
    @Singleton
    fun rssApi(okHttpClient: OkHttpClient): RssApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(rssApiEndpoint)
                .client(okHttpClient)
                .addConverterFactory(xmlDeserializer)
                .addCallAdapterFactory(callAdapter)
                .build()
        return retrofit.create(RssApi::class.java)
    }

    @Provides
    @Singleton
    fun dublinBusStopResource(
            dublinBusApi: DublinBusApi,
            rtpiApi: RtpiApi
    ): DublinBusStopResource {
        return DublinBusStopResource(dublinBusApi, rtpiApi)
    }

    @Provides
    @Singleton
    fun dublinBusRouteResource(
            dublinBusApi: DublinBusApi,
            rtpiApi: RtpiApi
    ): DublinBusRouteResource {
        return DublinBusRouteResource(dublinBusApi, rtpiApi)
    }

    @Provides
    @Singleton
    fun dublinBusLiveDataResource(
            dublinBusApi: DublinBusApi,
            rtpiApi: RtpiApi
    ): DublinBusLiveDataResource {
        return DublinBusLiveDataResource(dublinBusApi, rtpiApi)
    }

    @Provides
    @Singleton
    fun dublinBusRouteServiceResource(
            dublinBusApi: DublinBusApi,
            rtpiApi: RtpiApi
    ): DublinBusRouteServiceResource {
        return DublinBusRouteServiceResource(dublinBusApi, rtpiApi)
    }

    @Provides
    @Singleton
    fun dublinBusRssResource(rssApi: RssApi): DublinBusRssResource {
        return DublinBusRssResource(rssApi)
    }

}
