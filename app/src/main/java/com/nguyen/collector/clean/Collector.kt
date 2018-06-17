package com.nguyen.collector.clean

import android.os.Handler
import android.os.Looper

class Collector<T> {

    private var emitterListener: EmitterListener<T>? = null

    private var repeat = 0
    private var duration = 0L

    private fun threadPool(body: () -> Unit) {
        val thread = Thread {
            body()
            if (duration > 0) {
                if (repeat == 1) {
                    repeat--
                    return@Thread
                } else if (repeat > 1) {
                    repeat--
                }
                Thread.sleep(duration)
                threadPool(body)
            }
        }
        thread.start()
    }

    private fun mainThread(body: () -> Unit) {
        Handler(Looper.getMainLooper()).post {
            body()
        }
    }

    fun create(function: (EmitterListener<T>?) -> Unit): Collector<T> {
        threadPool {
            function(object : EmitterListener<T> {
                override fun onData(response: T) {
                    mainThread { emitterListener?.onData(response) }
                }

                override fun onError(error: Throwable) {
                    mainThread { emitterListener?.onError(error) }
                }

                override fun onFinish() {
                    mainThread { emitterListener?.onFinish() }
                }
            })
        }
        return this
    }

    fun emitterListener(emitter: EmitterListener<T>): Collector<T> {
        emitterListener = emitter
        return this
    }

    fun repeat(duration: Long, repeat: Int) {
        this.duration = duration
        this.repeat = repeat
    }
}