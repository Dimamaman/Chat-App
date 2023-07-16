package uz.gita.dima.chat_app.presenter.screen.login

interface LoginScreenDirection {

    suspend fun navigateToMain()

    suspend fun navigateToSignUp()
}