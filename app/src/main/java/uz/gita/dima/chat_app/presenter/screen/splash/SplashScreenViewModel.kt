package uz.gita.dima.chat_app.presenter.screen.splash

import kotlinx.coroutines.flow.SharedFlow

interface SplashScreenViewModel {

    val hasConnection: SharedFlow<Boolean>

    fun findScreen()

}