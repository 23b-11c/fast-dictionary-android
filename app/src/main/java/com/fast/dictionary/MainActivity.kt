package com.fast.dictionary

import android.os.Bundle
import com.fast.dictionary.core.CoreActivity

class MainActivity : CoreActivity() {

    private val mainFrame: MainFrame by replace(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
