package uz.gita.dima.chat_app.presenter.screen.main

import androidx.lifecycle.LiveData
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.presenter.screen.BaseViewModel

interface MainScreenViewModel : BaseViewModel {

    val allUser: LiveData<List<User>>

    fun getAllUsers()

    fun goChatScreen(user: User)

    fun logOut()

}