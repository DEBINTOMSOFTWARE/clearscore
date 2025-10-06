package com.dcs.clearscore

import androidx.test.espresso.IdlingResource

class ComposeIdlingResource : IdlingResource {
    private val resource = SimpleIdlingResource("ComposeIdlingResource")
    private var isIdle = true

    fun setIdle(isIdle: Boolean) {
        this.isIdle = isIdle
        if (isIdle) {
            resource.transitionToIdle()
        }
    }

    override fun getName(): String = resource.name

    override fun isIdleNow(): Boolean = isIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resource.registerIdleTransitionCallback(callback)
    }

    companion object {
        private val instance = ComposeIdlingResource()

        fun getInstance(): ComposeIdlingResource = instance

        fun increment() {
            getInstance().setIdle(false)
        }

        fun decrement() {
            getInstance().setIdle(true)
        }
    }
}
