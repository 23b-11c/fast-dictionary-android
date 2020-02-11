package com.fast.dictionary

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import com.fast.dictionary.core.ViewFrame
import com.fast.dictionary.extensions.showToast

class MainFrame: ViewFrame() {

    override val layoutResId: Int = R.layout.layout_main

    override fun onAttached(contentView: View) {
        super.onAttached(contentView)
        contentView.findViewById<EditText>(R.id.search)
            .setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.action == KeyEvent.ACTION_DOWN
                    && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    showToast("TODO: ${v.text} IME_ACTION_SEARCH")
                    true
                } else false
            }
    }
}