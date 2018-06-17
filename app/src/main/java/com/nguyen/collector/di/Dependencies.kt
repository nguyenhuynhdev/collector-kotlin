package com.nguyen.collector.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.nguyen.collector.di.scope.ActivityScope
import com.nguyen.collector.di.scope.FragmentScope
import java.util.concurrent.ConcurrentHashMap

class Dependencies private constructor() : DependenciesBuilder() {

    companion object {
        val instance: DependenciesBuilder by lazy {
            Dependencies()
        }
    }

    private val injectClass = ConcurrentHashMap<Class<*>, Any>()

    override fun builder(application: Application): DependenciesBuilder {
        application.registerActivityLifecycleCallbacks(object :Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
                clearScope(ActivityScope::class.java)
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                (p0 as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                        object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                                clearScope(FragmentScope::class.java)
                                super.onFragmentDestroyed(fm, f)
                            }
                        }, true)
            }

        })
        return this
    }

    override fun build(): Dependencies {
        return this
    }

    fun <T> insert(modelClass: Class<T>, value: T) {
        injectClass[modelClass] = value as Any
    }

    inline fun <reified T> add(value: T) {
        insert(T::class.java, value)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(modelClass: Class<T>): T = injectClass[modelClass] as T
            ?: throw IllegalArgumentException("unknown model class $modelClass")

    private fun clearScope(clazz: Class<out Annotation>) {
        injectClass.forEach { key: Class<*>, _ ->
            val isScope = key.isAnnotationPresent(clazz)
            if (isScope) {
                injectClass.remove(key)
            }
        }
    }
}
