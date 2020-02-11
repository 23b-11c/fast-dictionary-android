package com.fast.dictionary

import android.os.Bundle
import com.fast.dictionary.core.CoreActivity
import com.fast.dictionary.core.ViewFrame
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CoreActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(ViewFrame.newInstance(lifecycle, root, R.id.container, R.layout.layout_main)) {
            contentView?.let { addFrame(MainFrame(it, lifecycle)) }
        }
    }
}
