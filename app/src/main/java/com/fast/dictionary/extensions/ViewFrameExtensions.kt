package com.fast.dictionary.extensions

import android.widget.Toast
import com.fast.dictionary.core.ViewFrame

fun ViewFrame.showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    context?.let { Toast.makeText(it, text, length).show() }
}