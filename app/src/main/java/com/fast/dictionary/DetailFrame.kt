package com.fast.dictionary

import android.view.View
import com.fast.dictionary.core.ViewFrame
import io.reactivex.processors.BehaviorProcessor

class DetailFrame(word: String) : ViewFrame() {

    private lateinit var adapter: DetailAdapter

    override val layoutResId: Int = R.layout.layout_word_detail

    private val wordProcessor: BehaviorProcessor<String> = BehaviorProcessor.createDefault(word)

    override fun onAttached(contentView: View) {
        super.onAttached(contentView)

    }

    fun setWord(word: String) {
        wordProcessor.offer(word)
    }
}