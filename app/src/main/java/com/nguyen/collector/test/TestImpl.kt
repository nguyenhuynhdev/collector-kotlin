package com.nguyen.collector.test

import android.content.Context

class TestImpl constructor(private val context: Context): Test{
    override fun getName(): String {
        return context.applicationInfo.className
    }
}