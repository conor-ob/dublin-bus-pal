package ie.dublinbuspal.service.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class NetworkLoggingInterceptor : Interceptor {

    private val httpLoggingInterceptor: Interceptor by lazy { newHttpLoggingInterceptor() }
    private val log: Logger by lazy { newLogger() }

    override fun intercept(chain: Interceptor.Chain): Response {
        return httpLoggingInterceptor.intercept(chain)
    }

    private fun newLogger(): Logger {
        return LoggerFactory.getLogger(javaClass)
    }

    private fun newHttpLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            log.debug(it)
        })
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return interceptor
    }

}
