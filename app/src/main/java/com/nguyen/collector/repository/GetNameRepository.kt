package com.nguyen.collector.repository

import com.nguyen.collector.clean.Collector

interface GetNameRepository{

    fun get(): Collector<String>
}