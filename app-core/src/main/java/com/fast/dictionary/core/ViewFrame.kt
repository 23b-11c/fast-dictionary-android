package com.fast.dictionary.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import java.lang.ref.WeakReference

abstract class ViewFrame: LifecycleObserver {

    abstract val layoutResId: Int

    val context: Context?
        get() = weakContentView?.get()?.context

    val isShowing: Boolean
        get() = weakContentView?.get()?.visibility == View.VISIBLE

    private var weakContentView: WeakReference<View>? = null

    private var containerId: Int = 0

    internal fun getContainerId(): Int {
        return containerId
    }

    internal fun attach(activity: AppCompatActivity, containerId: Int) {
        this.containerId = containerId

        val containerView = activity.findViewById<ViewGroup>(containerId)
        LayoutInflater.from(activity).inflate(layoutResId, containerView, true).apply {
            weakContentView = WeakReference(this)
            onAttached(this)
        }

        activity.lifecycle.addObserver(this)
    }

    internal fun detach(activity: AppCompatActivity) {
        val containerView = activity.findViewById<ViewGroup?>(containerId) ?: return
        val contentView = weakContentView?.get() ?: return
        containerView.removeView(contentView)

        onLifecyclePause()
        onLifecycleStop()
        onLifecycleDestroy()

        weakContentView?.clear()
        onDetached()
    }

    protected open fun onAttached(contentView: View) {}

    protected open fun onDetached() {}

    fun show() {
        weakContentView?.get()?.visibility = View.VISIBLE
    }

    fun hide() {
        weakContentView?.get()?.visibility = View.GONE
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onLifecycleCreate() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onLifecycleStart() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onLifecycleResume() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onLifecyclePause() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onLifecycleStop() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onLifecycleDestroy() {}


}