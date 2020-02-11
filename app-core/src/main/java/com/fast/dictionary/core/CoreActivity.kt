package com.fast.dictionary.core

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

open class CoreActivity: AppCompatActivity() {

    private val frameStore: ViewFrameStore by lazy {
        ViewFrameStore(this)
    }

    protected val lazyStore: MutableSet<Pair<Lazy<ViewFrame>, Boolean>> = mutableSetOf()

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        lazyStore.forEach { (lazy, showOnCreate) ->
            when (showOnCreate) {
                true -> lazy.value.show()
                false -> lazy.value.hide()
            }

        }
    }

    protected inline fun <reified T: ViewFrame> CoreActivity.replace(@IdRes containerId: Int, showOnCreate: Boolean = true) = lazy {
        val modelClass = T::class.java
        return@lazy modelClass.newInstance().apply {
            replace(containerId, this)
        }
    }.apply { lazyStore.add(this to showOnCreate) }

    fun replace(@IdRes containerId: Int, frame: ViewFrame) {
        frameStore.replace(containerId, frame)
    }

    protected fun add(@IdRes containerId: Int, frame: ViewFrame) {
        frameStore.add(containerId, frame)
    }

    protected fun remove(frame: ViewFrame) {
        frameStore.remove(frame)
    }

    protected fun findFrameByContainerId(@IdRes containerId: Int): List<ViewFrame> {
        return frameStore.findFrameByContainerId(containerId)
    }

}