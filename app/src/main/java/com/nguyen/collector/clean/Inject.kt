package com.nguyen.collector.clean

import com.nguyen.collector.di.Dependencies

@Suppress("UNCHECKED_CAST")
fun <T> byInject(modelClass: Class<T>): T {
    try {
        return Dependencies.instance.build().get(modelClass)
    } catch (ex: IllegalArgumentException) {
        val linked = LinkedHashSet<Any>()
        modelClass.constructors.first().parameterTypes.forEach {
            val classModel = byInject(it)
            linked.add(classModel)
        }
        Dependencies.instance.build().insert(
                modelClass, modelClass.constructors.first().newInstance(*linked.toArray()) as T)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return Dependencies.instance.build().get(modelClass)
}