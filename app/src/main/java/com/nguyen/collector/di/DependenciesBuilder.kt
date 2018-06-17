package com.nguyen.collector.di

import android.app.Application

abstract class DependenciesBuilder {

    abstract fun builder(application: Application): DependenciesBuilder

    abstract fun build(): Dependencies

}