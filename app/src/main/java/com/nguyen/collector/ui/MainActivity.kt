package com.nguyen.collector.ui

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.NetworkOnMainThreadException
import android.support.annotation.WorkerThread
import android.support.v7.app.AppCompatActivity

import com.nguyen.collector.R
import com.nguyen.collector.clean.byInject
import com.nguyen.collector.model.User
import com.nguyen.collector.presentation.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val mainPresenter = byInject(MainPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter.setView(this)
        btnNext.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
            finish()
        }
    }

    override fun setText(text: String) {
        txtTest.text = text
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
}
