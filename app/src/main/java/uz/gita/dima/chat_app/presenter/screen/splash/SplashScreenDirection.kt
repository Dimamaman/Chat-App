package uz.gita.dima.chat_app.presenter.screen.splash

interface SplashScreenDirection {

    suspend fun navigateToMain()

    suspend fun navigateToLogin()

}