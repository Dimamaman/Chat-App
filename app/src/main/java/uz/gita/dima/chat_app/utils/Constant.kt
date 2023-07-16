package uz.gita.dima.chat_app.utils

import androidx.viewbinding.ViewBinding

object Constant {
    const val SHARED_PREF = "shared_pref"
    const val NAME = "name"
    const val EMAIL = "email"
    const val PASSWORD = "password"
    const val UUID = "uuid"
    const val IS_LOGGED = "is_logged"
}

fun <T : ViewBinding> T.include(block: T.() -> Unit) {
    block(this)
}

