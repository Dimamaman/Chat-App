package uz.gita.dima.chat_app.presenter.screen.splash

import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.navigation.Navigator
import javax.inject.Inject

class SplashScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : SplashScreenDirection{
    override suspend fun navigateToMain() {
        navigator.navigateTo(SplashScreenDirections.actionSplashScreenToMainScreen())
    }

    override suspend fun navigateToLogin() {
        navigator.navigateTo(SplashScreenDirections.actionSplashScreenToLoginScreen())
    }
}