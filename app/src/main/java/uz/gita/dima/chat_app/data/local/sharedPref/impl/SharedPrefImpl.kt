package uz.gita.dima.chat_app.data.local.sharedPref.impl

import android.content.SharedPreferences
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.utils.Constant.EMAIL
import uz.gita.dima.chat_app.utils.Constant.NAME
import uz.gita.dima.chat_app.utils.Constant.PASSWORD
import uz.gita.dima.chat_app.utils.Constant.UUID
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(private val pref: SharedPreferences) : SharedPref {

    override var name: String?
        get() = pref.getString(NAME, "")
        set(value) = pref.edit().putString(NAME, value).apply()

    override var email: String?
        get() = pref.getString(EMAIL, "")
        set(value) { pref.edit().putString(EMAIL, value).apply() }

    override var password: String?
        get() = pref.getString(PASSWORD, "")
        set(value) = pref.edit().putString(PASSWORD, value).apply()

    override var uuid: String?
        get() = pref.getString(UUID, "")
        set(value) = pref.edit().putString(UUID, value).apply()

}