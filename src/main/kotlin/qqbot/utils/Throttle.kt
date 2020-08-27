package qqbot.utils

import kotlinx.coroutines.delay

class Throttle(val time: Long = 5000L) {

    var isRunning = false

    suspend inline fun run(action: () -> Unit) {
        if (isRunning) {
            return
        }
        isRunning = true
        action()
        delay(time)
        isRunning = false
    }

}