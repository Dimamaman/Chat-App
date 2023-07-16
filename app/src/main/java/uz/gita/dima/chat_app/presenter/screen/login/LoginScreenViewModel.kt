package uz.gita.dima.chat_app.presenter.screen.login

import uz.gita.dima.chat_app.presenter.screen.BaseViewModel

interface LoginScreenViewModel : BaseViewModel{

    fun login(email: String, password: String)

    fun navigateToSignUp()

}