package com.nguyen.collector

import android.app.Application
import com.nguyen.collector.clean.byInject
import com.nguyen.collector.di.Dependencies
import com.nguyen.collector.repository.GetNameDataRepository
import com.nguyen.collector.repository.GetNameRepository
import com.nguyen.collector.test.Test
import com.nguyen.collector.test.TestImpl

class AndroidApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Dependencies.instance.builder(this).build().apply {
            //Singleton module
            add(applicationContext)
            add(byInject(TestImpl::class.java) as Test)
            add(byInject(GetNameDataRepository::class.java) as GetNameRepository)
        }
    }

}