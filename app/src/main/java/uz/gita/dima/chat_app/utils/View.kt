package uz.gita.dima.chat_app.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(this.context).inflate(resId, this, false)
}