package uz.gita.dima.chat_app.presenter.screen.chat

import androidx.lifecycle.ViewModel
import uz.gita.dima.chat_app.navigation.Navigator
import javax.inject.Inject

class ChatScreenDirectionImp @Inject constructor(
    private val navigator: Navigator
) : ChatScreenDirection {
    override suspend fun navigateToMain() {
        navigator.back()
    }
}