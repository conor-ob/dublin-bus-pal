package ie.dublinbuspal.android.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import ie.dublinbuspal.android.data.DublinBusRepository;
import ie.dublinbuspal.android.data.DublinBusRepositoryImpl;
import ie.dublinbuspal.android.data.local.DbInfo;
import ie.dublinbuspal.android.data.local.DublinBusDatabase;
import ie.dublinbuspal.android.data.local.LocalDataSource;
import ie.dublinbuspal.android.data.local.LocalDataSourceImpl;
import ie.dublinbuspal.android.data.local.PreferencesDataSource;
import ie.dublinbuspal.android.data.local.PreferencesDataSourceImpl;
import ie.dublinbuspal.android.data.memory.CacheDataSource;
import ie.dublinbuspal.android.data.memory.CacheDataSourceImpl;
import ie.dublinbuspal.android.data.remote.RemoteDataSource;
import ie.dublinbuspal.android.data.remote.download.DownloadProgressInterceptor;
import ie.dublinbuspal.android.data.remote.download.DownloadProgressListener;
import ie.dublinbuspal.android.data.remote.download.DownloadProgressListenerImpl;
import ie.dublinbuspal.android.data.remote.logging.NetworkLoggingInterceptor;
import ie.dublinbuspal.android.data.remote.rest.DublinBusRestApi;
import ie.dublinbuspal.android.data.remote.rest.DublinBusRestServiceAdapter;
import ie.dublinbuspal.android.data.remote.rest.RestDataSource;
import ie.dublinbuspal.android.data.remote.rss.DublinBusRssApi;
import ie.dublinbuspal.android.data.remote.rss.DublinBusRssServiceAdapter;
import ie.dublinbuspal.android.data.remote.rss.RssDataSource;
import ie.dublinbuspal.android.data.remote.soap.DublinBusSoapServiceAdapter;
import ie.dublinbuspal.android.data.remote.soap.DublinBusSoapServiceApi;
import ie.dublinbuspal.android.util.InternetManager;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class RepositoryModule {

    private static final String DATABASE_NAME = "database_name";
    private static final String SOAP_SERVICE_RETROFIT = "soap_service_retrofit";
    private static final String SOAP_SERVICE_BASE_URL = "soap_service_base_url";
    private static final String SOAP_SERVICE_CONVERTER_FACTORY = "soap_service_converter_factory";
    private static final String REST_SERVICE_RETROFIT = "rest_service_retrofit";
    private static final String REST_SERVICE_BASE_URL = "rest_service_base_url";
    private static final String REST_SERVICE_CONVERTER_FACTORY = "rest_service_converter_factory";
    private static final String RSS_SERVICE_RETROFIT = "rss_service_retrofit";
    private static final String RSS_SERVICE_BASE_URL = "rss_service_base_url";
    private static final String RSS_SERVICE_CONVERTER_FACTORY = "rss_service_converter_factory";
    private static final String DONWLOAD_INTERCEPTOR = "download_interceptor";
    private static final String LOGGING_INTERCEPTOR = "logging interceptor";

    @Provides
    @Singleton
    DublinBusRepository dublinBusRepository(CacheDataSource cache, LocalDataSource database,
                                                   RemoteDataSource soapService,
                                                   RestDataSource restApi,
                                                   RssDataSource rssFeed,
                                                   PreferencesDataSource preferences,
                                                   InternetManager internetManager) {

        DublinBusRepository repository = new DublinBusRepositoryImpl(cache, database, soapService,
                restApi, rssFeed, preferences, internetManager);

        Single.fromCallable(repository::getBusStops)
                .subscribeOn(Schedulers.io())
                .subscribe();

        Single.fromCallable(repository::getRoutes)
                .subscribeOn(Schedulers.io())
                .subscribe();

        Single.fromCallable(repository::getBusStopServices)
                .subscribeOn(Schedulers.io())
                .subscribe();

        return repository;
    }

    @Provides
    @Singleton
    CacheDataSource cacheDataSource() {
        return new CacheDataSourceImpl();
    }

    @Provides
    @Singleton
    LocalDataSource localDataSource(DublinBusDatabase database) {
        return new LocalDataSourceImpl(database);
    }

    @Provides
    @Singleton
    DublinBusDatabase dublinBusDatabase(Context context, @Named(DATABASE_NAME) String name) {
        return Room.databaseBuilder(context, DublinBusDatabase.class, name).build();
    }

    @Provides
    @Named(DATABASE_NAME)
    String databaseName() {
        return DbInfo.DB_NAME;
    }

    @Provides
    @Singleton
    RemoteDataSource soapDataSource(DublinBusSoapServiceApi api) {
        return new DublinBusSoapServiceAdapter(api);
    }

    @Provides
    @Named(SOAP_SERVICE_BASE_URL)
    String provideSoapServiceBaseUrl() {
        return DublinBusSoapServiceApi.BASE_URL;
    }

    @Provides
    @Singleton
    DublinBusSoapServiceApi provideDublinBusSoapServiceApi(@Named(SOAP_SERVICE_RETROFIT)
                                                                              Retrofit retrofit) {
        return retrofit.create(DublinBusSoapServiceApi.class);
    }

    @Provides
    @Singleton
    @Named(SOAP_SERVICE_RETROFIT)
    Retrofit provideSoapServiceRetrofit(OkHttpClient okHttpClient,
                                               @Named(SOAP_SERVICE_BASE_URL) String baseUrl,
                                               @Named(SOAP_SERVICE_CONVERTER_FACTORY)
                                                           Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build();
    }

    @Provides
    @Singleton
    @Named(SOAP_SERVICE_CONVERTER_FACTORY)
    Converter.Factory provideSimpleXmlConverterFactory(Serializer serializer) {
        return SimpleXmlConverterFactory.create(serializer);
    }

    @Provides
    @Singleton
    Serializer providePersister(Strategy strategy) {
        return new Persister(strategy);
    }

    @Provides
    @Singleton
    Strategy provideStrategy() {
        return new AnnotationStrategy();
    }

    @Provides
    @Singleton
    RestDataSource restDataSource(DublinBusRestApi api) {
        return new DublinBusRestServiceAdapter(api);
    }

    @Provides
    @Singleton
    DublinBusRestApi dublinBusRestApi(@Named(REST_SERVICE_RETROFIT) Retrofit retrofit) {
        return retrofit.create(DublinBusRestApi.class);
    }

    @Provides
    @Singleton
    @Named(REST_SERVICE_RETROFIT)
    Retrofit provideRestRetrofit(OkHttpClient okHttpClient,
                                        @Named(REST_SERVICE_BASE_URL) String baseUrl,
                                        @Named(REST_SERVICE_CONVERTER_FACTORY)
                                                Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(@Named(DONWLOAD_INTERCEPTOR) Interceptor downloadInterceptor,
                              @Named(LOGGING_INTERCEPTOR) Interceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(downloadInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @Named(DONWLOAD_INTERCEPTOR)
    Interceptor provideInterceptor(DownloadProgressListener listener) {
        return new DownloadProgressInterceptor(listener);
    }

    @Provides
    @Singleton
    @Named(LOGGING_INTERCEPTOR)
    Interceptor provideLoggingInterceptor() {
        return new NetworkLoggingInterceptor();
    }

    @Provides
    @Singleton
    DownloadProgressListener provideDownloadProgressListener() {
        return new DownloadProgressListenerImpl();
    }

    @Provides
    @Named(REST_SERVICE_BASE_URL)
    String provideRestApiBaseUrl() {
        return DublinBusRestApi.BASE_URL;
    }

    @Provides
    @Singleton
    @Named(REST_SERVICE_CONVERTER_FACTORY)
    Converter.Factory converterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RssDataSource rssDataSource(DublinBusRssApi api) {
        return new DublinBusRssServiceAdapter(api);
    }

    @Provides
    @Singleton
    DublinBusRssApi dublinBusRssApi(@Named(RSS_SERVICE_RETROFIT) Retrofit retrofit) {
        return retrofit.create(DublinBusRssApi.class);
    }

    @Provides
    @Singleton
    @Named(RSS_SERVICE_RETROFIT)
    Retrofit rssRetrofit(OkHttpClient okHttpClient, @Named(RSS_SERVICE_BASE_URL) String baseUrl,
                         @Named(RSS_SERVICE_CONVERTER_FACTORY) Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build();
    }

    @Provides
    @Singleton
    @Named(RSS_SERVICE_CONVERTER_FACTORY)
    Converter.Factory provideRssServiceConverterFactory() {
        return SimpleXmlConverterFactory.create();
    }

    @Provides
    @Named(RSS_SERVICE_BASE_URL)
    String provideRssApiBaseUrl() {
        return DublinBusRssApi.BASE_URL;
    }

    @Provides
    @Singleton
    PreferencesDataSource preferencesHelper(Context context) {
        return new PreferencesDataSourceImpl(context);
    }

    @Provides
    @Singleton
    InternetManager internetManager(Context context) {
        return new InternetManager(context);
    }

}
