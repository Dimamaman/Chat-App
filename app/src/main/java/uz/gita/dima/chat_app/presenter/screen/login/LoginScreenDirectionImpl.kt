package uz.gita.dima.chat_app.presenter.screen.login

import uz.gita.dima.chat_app.navigation.Navigator
import javax.inject.Inject

class LoginScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : LoginScreenDirection{
    override suspend fun navigateToMain() {
        navigator.navigateTo(LoginScreenDirections.actionLoginScreenToMainScreen())
    }

    override suspend fun navigateToSignUp() {
        navigator.navigateTo(LoginScreenDirections.actionLoginScreenToSignUpScreen())
    }
}