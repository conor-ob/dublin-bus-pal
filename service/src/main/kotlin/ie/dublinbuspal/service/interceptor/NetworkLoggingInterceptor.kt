package ie.dublinbuspal.service.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.slf4j.LoggerFactory

class NetworkLoggingInterceptor : Interceptor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        log.debug("sending request ${request.url()} on ${chain.connection()}\n${request.headers()}")

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        log.debug("received response for ${response.request().url()} in ${(t2 - t1) / 1e6} ms\n${response.headers()}")

        return response
    }

}
