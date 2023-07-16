package uz.gita.dima.chat_app.presenter.screen.signup

interface SignUpScreenDirection {

    suspend fun navigateToLogin()

    suspend fun navigateToMain()

}