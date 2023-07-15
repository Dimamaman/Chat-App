package uz.gita.dima.chat_app.presenter.screen.signup

import uz.gita.dima.chat_app.presenter.screen.BaseViewModel

interface SignUpViewModel : BaseViewModel {

    fun signUp(name: String, email: String, password: String)
}