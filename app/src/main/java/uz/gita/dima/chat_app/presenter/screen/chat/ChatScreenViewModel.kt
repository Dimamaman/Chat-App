package uz.gita.dima.chat_app.presenter.screen.chat

import androidx.lifecycle.LiveData
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.presenter.screen.BaseViewModel

interface ChatScreenViewModel : BaseViewModel {

    val allMessage: LiveData<List<Message>>

    fun getMessagesBySender(sender: String)

    fun back()

    fun sendMessage(sender: String, receiver: String, messageObject: Message)
}