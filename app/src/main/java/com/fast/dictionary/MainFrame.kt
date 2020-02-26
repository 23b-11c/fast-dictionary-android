package com.fast.dictionary

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.fast.dictionary.core.ViewFrame
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainFrame : ViewFrame() {

    override val layoutResId: Int = R.layout.layout_main

    private val onSearchClickSubject: PublishSubject<String> = PublishSubject.create()

    override fun onAttached(contentView: View) {
        super.onAttached(contentView)
        contentView.findViewById<EditText>(R.id.search)
            .setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.action == KeyEvent.ACTION_DOWN
                    && event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    onSearchClickSubject.onNext(v.text.toString())
                    true
                } else false
            }
    }

    fun observeSearchRequest(): Observable<String> = onSearchClickSubject
}