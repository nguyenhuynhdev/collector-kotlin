package com.nguyen.collector.repository

import com.nguyen.collector.test.Test
import com.nguyen.collector.clean.Collector

class GetNameDataRepository constructor(private val test: Test): GetNameRepository{

    var i = 0
    override fun get(): Collector<String> {
        return Collector<String>().create {
            try {
                it?.onData(i.toString())
            } catch (ex: Exception) {
                it?.onError(ex)
            }
            i++
        }
    }

}