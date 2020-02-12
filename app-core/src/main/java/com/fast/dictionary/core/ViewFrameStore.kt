package com.fast.dictionary.core

import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference

internal class ViewFrameStore(activity: CoreActivity): LifecycleObserver {

    init {
        activity.lifecycle.addObserver(this)
    }

    private val weakActivity = WeakReference<CoreActivity>(activity)
    private val viewFrames = mutableListOf<ViewFrame>()

    fun replace(@IdRes containerId: Int, frame: ViewFrame) {
        val activity = weakActivity.get() ?: return
        val frames = findFrameByContainerId(containerId)
        frames.forEach { it.detach(activity) }
        viewFrames.removeAll(viewFrames)

        frame.attach(activity, containerId)
        viewFrames.add(frame)
    }

    fun add(@IdRes containerId: Int, frame: ViewFrame) {
        val activity = weakActivity.get() ?: return
        frame.attach(activity, containerId)
        viewFrames.add(frame)
    }

    fun remove(frame: ViewFrame) {
        val activity = weakActivity.get() ?: return
        frame.detach(activity)
        viewFrames.remove(frame)
    }

    fun findFrameByContainerId(@IdRes containerId: Int): List<ViewFrame> {
        return frames().filter { it.getContainerId() == containerId }
    }

    fun frames(): List<ViewFrame> {
        return viewFrames
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifecycleDestroyed() {
        val activity = weakActivity.get() ?: return
        viewFrames.forEach { it.detach(activity) }
        viewFrames.clear()
    }
}