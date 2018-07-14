package ie.dublinbuspal.service.di

import dagger.Module
import dagger.Provides
import ie.dublinbuspal.service.DublinBusApi
import ie.dublinbuspal.service.interceptor.NetworkLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(private val apiEndpoint: String) {

    @Provides
    @Singleton
    fun okHttpClient(loggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                //.addInterceptor(downloadInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun dublinBusApi(retrofit: Retrofit): DublinBusApi = retrofit.create(DublinBusApi::class.java)

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient,
                 converterFactory: Converter.Factory,
                 callAdapterFactory: CallAdapter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(client)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun networkLoggingInterceptor(): Interceptor = NetworkLoggingInterceptor()

    @Provides
    @Singleton
    fun converterFactoryXml(): Converter.Factory = SimpleXmlConverterFactory.create()

    @Provides
    @Singleton
    fun callAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

}
