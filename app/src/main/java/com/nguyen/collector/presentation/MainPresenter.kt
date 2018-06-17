package com.nguyen.collector.presentation

import android.content.Context
import com.nguyen.collector.ui.MainView
import com.nguyen.collector.clean.EmitterListener
import com.nguyen.collector.clean.GetNameUseCase
import com.nguyen.collector.di.scope.ActivityScope

@ActivityScope
class MainPresenter constructor(
        private val context: Context,
        private val useCase: GetNameUseCase) {

    private var mainView: MainView? = null

    fun setView(mainView: MainView) {
        this.mainView = mainView
        useCase.execute(event, null)
    }

    private val event = object : EmitterListener<String> {
        override fun onData(response: String) {
            mainView?.setText(response)

        }

        override fun onError(error: Throwable) {
            mainView?.setText(error.localizedMessage)
        }

        override fun onFinish() {
        }
    }
}