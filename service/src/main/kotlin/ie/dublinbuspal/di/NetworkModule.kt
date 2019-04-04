package ie.dublinbuspal.di

import dagger.Module
import dagger.Provides
import ie.dublinbuspal.service.Client
import ie.dublinbuspal.service.ClientImpl
import ie.dublinbuspal.service.api.DublinBusGoAheadDublinRestApi
import ie.dublinbuspal.service.api.DublinBusRssApi
import ie.dublinbuspal.service.api.DublinBusSoapApi
import ie.dublinbuspal.service.interceptor.NetworkLoggingInterceptor
import ie.dublinbuspal.service.resource.*
import ie.rtpi.RtpiClient
import ie.rtpi.Service
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(
        private val soapApiEndpoint: String,
        private val restApiEndpoint: String,
        private val rssApiEndpoint: String
) {

    private val callAdapter: CallAdapter.Factory by lazy { RxJava2CallAdapterFactory.create() }
    private val jsonDeserializer: Converter.Factory by lazy { GsonConverterFactory.create() }
    private val xmlDeserializer: Converter.Factory by lazy { SimpleXmlConverterFactory.create() }
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                //.addInterceptor(downloadInterceptor)
                .addNetworkInterceptor(NetworkLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun client(): Client {
        return ClientImpl(RtpiClient(EnumSet.of(Service.DUBLIN_BUS)).dublinBus())
    }

    @Provides
    @Singleton
    fun dublinBusSoapResource(): DublinBusSoapResource {
        val retrofit = Retrofit.Builder()
                .baseUrl(soapApiEndpoint)
                .client(okHttpClient)
                .addConverterFactory(xmlDeserializer)
                .addCallAdapterFactory(callAdapter)
                .build()
        val api = retrofit.create(DublinBusSoapApi::class.java)
        return DublinBusSoapResourceAdapter(api)
    }

    @Provides
    @Singleton
    fun smartDublinRestResource(): DublinBusGoAheadDublinRestResource {
        val retrofit = Retrofit.Builder()
                .baseUrl(restApiEndpoint)
                .client(okHttpClient)
                .addConverterFactory(jsonDeserializer)
                .addCallAdapterFactory(callAdapter)
                .build()
        val api = retrofit.create(DublinBusGoAheadDublinRestApi::class.java)
        return DublinBusGoAheadDublinRestResourceAdapter(api)
    }

    @Provides
    @Singleton
    fun dublinBusRssResource(): DublinBusRssResource {
        val retrofit = Retrofit.Builder()
                .baseUrl(rssApiEndpoint)
                .client(okHttpClient)
                .addConverterFactory(xmlDeserializer)
                .addCallAdapterFactory(callAdapter)
                .build()
        val api = retrofit.create(DublinBusRssApi::class.java)
        return DublinBusRssResourceAdapter(api)
    }

}
