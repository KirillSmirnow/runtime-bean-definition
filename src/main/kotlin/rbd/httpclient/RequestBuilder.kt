package rbd.httpclient

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import java.lang.reflect.Method

class RequestBuilder(
    private val httpClient: HttpClient,
    private val method: Method,
    private val arguments: Array<*>,
) {

    private val httpMethod: HttpMethod
    private val path: String

    init {
        when {
            method.isAnnotationPresent(GetMapping::class.java) -> {
                httpMethod = HttpMethod.GET
                path = method.getAnnotation(GetMapping::class.java).value[0]
            }

            else -> throw IllegalStateException("Method unsupported: $method")
        }
    }

    fun getUrl(): String = "${httpClient.url}$path"

    fun getMethod(): HttpMethod = httpMethod

    fun getRequestEntity(): HttpEntity<*>? = null

    fun getResponseType(): Class<*> = method.returnType

    fun getPathVariables(): Map<String, *> {
        return method.parameters.mapIndexed { index, parameter ->
            Pair(parameter.name, arguments[index])
        }.toMap()
    }
}
