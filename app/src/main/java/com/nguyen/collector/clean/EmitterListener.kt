package com.nguyen.collector.clean

interface EmitterListener<T> {

    fun onData(response: T){}

    fun onError(error: Throwable){}

    fun onFinish(){}
}