package com.fast.dictionary.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import java.lang.ref.WeakReference

open class ViewFrame(
    contentView: View,
    private val lifecycle: Lifecycle
): LifecycleObserver, LifecycleOwner {

    companion object {
        fun newInstance(lifecycle: Lifecycle, root: ViewGroup, containerId: Int, layoutId: Int): ViewFrame {
            val container = root.findViewById<ViewGroup>(containerId)
            val contentView = LayoutInflater.from(root.context).inflate(layoutId, null)
            container.addView(contentView)
            return ViewFrame(contentView, lifecycle)
        }
    }

    val context: Context?
        get() = contentView?.context

    val contentView: View?
        get() = weakContentView.get()

    val isShowing: Boolean
        get() = contentView?.visibility == View.VISIBLE

    private val weakContentView = WeakReference<View>(contentView)
    private val lifecycleRegistry by lazy {
        LifecycleRegistry(this@ViewFrame)
    }

    private val frames = hashSetOf<ViewFrame>()

    fun show() {
        contentView?.visibility = View.VISIBLE
    }

    fun hide() {
        contentView?.visibility = View.GONE
    }

    fun remove() {
        onLifecyclePause()
        onLifecycleStop()
        onLifecycleDestroy()

        val root = contentView?.parent as? ViewGroup
        root?.removeView(contentView)
    }

    protected fun addFrame(frame: ViewFrame) {
        frames.add(frame)
        lifecycle.addObserver(frame)
    }

    protected fun removeFrame(frame: ViewFrame) {
        lifecycle.removeObserver(frame)
        frames.remove(frame)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onLifecycleCreate() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onLifecycleStart() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onLifecycleResume() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onLifecyclePause() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onLifecycleStop() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onLifecycleDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

        frames.forEach(lifecycle::removeObserver)
        frames.clear()

        weakContentView.clear()
    }
}