package qqbot.utils

import kotlinx.coroutines.delay

class Throttle {

    var isRunning = false

    suspend inline fun run(action: () -> Unit) {
        if (isRunning) {
            return
        }
        isRunning = true
        action()
        delay(5000)
        isRunning = false
    }

}