package uz.gita.dima.chat_app.presenter.screen.signup

import uz.gita.dima.chat_app.navigation.Navigator
import javax.inject.Inject


class SignUpScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : SignUpScreenDirection {
    override suspend fun navigateToLogin() {
        navigator.back()
    }

    override suspend fun navigateToMain() {
        navigator.navigateTo(SignUpScreenDirections.actionSignUpScreenToMainScreen())
    }
}