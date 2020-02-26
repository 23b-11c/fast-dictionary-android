package com.fast.dictionary

import android.os.Bundle
import com.fast.dictionary.core.CoreActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivity : CoreActivity() {

    private val mainFrame: MainFrame by replace(R.id.container)
    private lateinit var detailFrame: DetailFrame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFrame.observeSearchRequest()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(AndroidLifecycleScopeProvider.from(this))
            .subscribe {
                detailFrame = DetailFrame(it)
                replace(R.id.container, detailFrame)
            }
    }
}
