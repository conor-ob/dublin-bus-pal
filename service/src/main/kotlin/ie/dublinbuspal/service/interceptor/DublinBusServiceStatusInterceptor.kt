package ie.dublinbuspal.service.interceptor

import ie.dublinbuspal.service.DublinBusSoapApi
import ie.dublinbuspal.service.model.status.ServiceStatusRequestBodyXml
import ie.dublinbuspal.service.model.status.ServiceStatusRequestRootXml
import ie.dublinbuspal.service.model.status.ServiceStatusRequestXml
import okhttp3.Interceptor
import okhttp3.Response

class DublinBusServiceStatusInterceptor(private val api: DublinBusSoapApi) : Interceptor {

    private val request: ServiceStatusRequestXml by lazy {
        val root = ServiceStatusRequestRootXml()
        val body = ServiceStatusRequestBodyXml(root)
        return@lazy ServiceStatusRequestXml(body)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val status = api.getServiceStatus(request)
        val request = chain.request()
        return chain.proceed(request)
    }

}