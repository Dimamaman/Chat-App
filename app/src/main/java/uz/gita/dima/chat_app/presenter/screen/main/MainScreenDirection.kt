package uz.gita.dima.chat_app.presenter.screen.main

import uz.gita.dima.chat_app.data.common.User

interface MainScreenDirection {

    suspend fun goChatScreen(user: User)

    suspend fun goSplash()

}