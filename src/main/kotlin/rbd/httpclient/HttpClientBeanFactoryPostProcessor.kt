package rbd.httpclient

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.reflect.Proxy

@Component
class HttpClientBeanFactoryPostProcessor : BeanFactoryPostProcessor {

    private val log = LoggerFactory.getLogger(javaClass)
    private val basePackageToScan = "rbd.demo"
    private val restTemplate = RestTemplate()

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        ReflectionUtility.findTypesAnnotatedWith(HttpClient::class.java, basePackageToScan).forEach { client ->
            log.info("HTTP client found: $client")
            val httpClient = client.getAnnotation(HttpClient::class.java)
            val instance = Proxy.newProxyInstance(client.classLoader, arrayOf(client)) { _, method, arguments ->
                val requestBuilder = RequestBuilder(httpClient, method, arguments)
                return@newProxyInstance restTemplate.exchange(
                    requestBuilder.getUrl(),
                    requestBuilder.getMethod(),
                    requestBuilder.getRequestEntity(),
                    requestBuilder.getResponseType(),
                    requestBuilder.getPathVariables(),
                ).body
            }
            beanFactory.registerSingleton(client.simpleName, instance)
        }
    }
}
