package rbd.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import rbd.httpclient.HttpClient

@HttpClient("https://agibase.ru")
interface AgiBaseHttpClient {

    @GetMapping("/api/sportsmen/{id}")
    fun getSportsman(@PathVariable id: Int): String
}
