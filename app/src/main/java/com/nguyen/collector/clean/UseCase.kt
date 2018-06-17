package com.nguyen.collector.clean

abstract class UseCase<T, in P>{

    abstract fun buildUseCase(param: P): Collector<T>

    fun execute(emitter: EmitterListener<T>, param: P){
        buildUseCase(param).emitterListener(emitter)
    }
}