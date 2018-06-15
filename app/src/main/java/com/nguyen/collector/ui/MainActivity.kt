package com.nguyen.collector.ui

import android.os.Bundle
import android.os.Looper
import android.os.NetworkOnMainThreadException
import android.support.annotation.WorkerThread
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.nguyen.collector.R
import com.nguyen.collector.model.User
import com.nguyen.collector.clean.EmitterListener
import com.nguyen.collector.clean.Collector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val collector = Collector<User>()
        var i =0
        collector.create {
            try {
                it?.onData(getUser(i))
                i++
            } catch (ex: Exception) {
                it?.onError(ex)
            }
        }.emitterListener(event).repeat(1000, 10)
    }

    @WorkerThread
    fun getUser(id: Int): User {
        checkMainThread()
        return User(id, "Nguyen Huynh")
    }

    @WorkerThread
    private fun checkMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw NetworkOnMainThreadException()
        }
    }

    private val event = object : EmitterListener<User> {
        override fun onData(response: User) {
            txtTest.text = response.id.toString()
        }

        override fun onError(error: Throwable) {
            txtTest.text = "Error"
            Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_SHORT).show()
        }

        override fun onFinish() {
        }
    }

}