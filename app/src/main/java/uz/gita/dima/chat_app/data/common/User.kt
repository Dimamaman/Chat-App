package uz.gita.dima.chat_app.data.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val uid: String? = null
): Parcelable