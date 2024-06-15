package rbd.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DemoRunner(
    private val agiBaseHttpClient: AgiBaseHttpClient,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener(ApplicationReadyEvent::class)
    fun run() {
        val sportsmanId = 1
        val sportsman = agiBaseHttpClient.getSportsman(sportsmanId)
        log.info("Sportsman $sportsmanId: $sportsman")
    }
}
