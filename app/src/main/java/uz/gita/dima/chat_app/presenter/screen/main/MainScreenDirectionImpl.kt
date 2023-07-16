package uz.gita.dima.chat_app.presenter.screen.main

import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.navigation.Navigator
import javax.inject.Inject

class MainScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : MainScreenDirection {

    override suspend fun goChatScreen(user: User) {
        navigator.navigateTo(MainScreenDirections.actionMainScreenToChatScreen(user))
    }

    override suspend fun goSplash() {
        navigator.navigateTo(MainScreenDirections.actionMainScreenToSplashScreen())
    }
}