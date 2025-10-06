package com.dcs.clearscore

import androidx.test.espresso.IdlingResource

class SimpleIdlingResource(private val resourceName: String) : IdlingResource {
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = resourceName

    override fun isIdleNow(): Boolean = true

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    fun transitionToIdle() {
        callback?.onTransitionToIdle()
    }
}
