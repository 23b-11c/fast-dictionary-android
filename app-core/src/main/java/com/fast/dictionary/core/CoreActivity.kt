package com.fast.dictionary.core

import androidx.appcompat.app.AppCompatActivity

class CoreActivity: AppCompatActivity() {

    private val frames = hashSetOf<ViewFrame>()

    protected fun addFrame(frame: ViewFrame) {
        frames.add(frame)
        lifecycle.addObserver(frame)
    }

    protected fun removeFrame(frame: ViewFrame) {
        lifecycle.removeObserver(frame)
        frames.remove(frame)
    }

    override fun onDestroy() {
        super.onDestroy()
        frames.forEach(lifecycle::removeObserver)
        frames.clear()
    }

}