package ie.dublinbuspal.service.di

import dagger.Module
import dagger.Provides
import ie.dublinbuspal.service.DublinBusApi
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                //.addInterceptor(downloadInterceptor)
                //.addNetworkInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideDublinBusApi(retrofit: Retrofit): DublinBusApi
            = retrofit.create(DublinBusApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, converterFactory: Converter.Factory,
                        callAdapterFactory: CallAdapter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://rtpi.dublinbus.ie/")
                .client(client)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactoryXml(): Converter.Factory = SimpleXmlConverterFactory.create()

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

}
