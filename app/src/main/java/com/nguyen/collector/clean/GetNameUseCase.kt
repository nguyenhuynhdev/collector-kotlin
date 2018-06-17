package com.nguyen.collector.clean

import com.nguyen.collector.repository.GetNameRepository

class GetNameUseCase constructor(private val repository: GetNameRepository) : UseCase<String, Void?>() {
    override fun buildUseCase(param: Void?): Collector<String> = repository.get()
}