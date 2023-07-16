package uz.gita.dima.chat_app.presenter.screen.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.domain.usecase.AuthUseCase
import uz.gita.dima.chat_app.utils.ResultData
import uz.gita.dima.chat_app.utils.hasConnection
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModelImp @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val direction: ChatScreenDirection
) : ChatScreenViewModel, ViewModel() {

    override val loadingSharedFlow = MutableSharedFlow<Boolean>()

    override val messageSharedFlow = MutableSharedFlow<String>()

    override val errorSharedFlow = MutableSharedFlow<String>()

    override val allMessage = MutableLiveData<List<Message>>()

    override fun getMessagesBySender(sender: String) {
        viewModelScope.launch() {
            if (hasConnection()) {
                authUseCase.getAllMessagesBySender(sender).onEach {
                    when (it) {

                        is ResultData.Success -> {
                            it.onSuccess { messageList ->
                                Log.d("GGG","New message list -> $messageList\n")
                                allMessage.value = messageList.reversed()
                            }
                        }

                        is ResultData.Message -> {
                            it.onMessage { errorMessage ->
                                messageSharedFlow.emit(errorMessage)
                            }
                        }

                        is ResultData.Error -> {
                            // Error joq
                        }

                    }
                }.launchIn(viewModelScope)


            } else {
                messageSharedFlow.emit("No Internet Connection")
            }
        }
    }

    override fun back() {
        viewModelScope.launch {
            direction.navigateToMain()
        }
    }

    override fun sendMessage(sender: String, receiver: String, messageObject: Message) {
        viewModelScope.launch {
            if (hasConnection()) {
                authUseCase.sendMessage(sender, receiver, messageObject).onEach {
                    when(it) {
                        is ResultData.Success -> {
                            it.onSuccess { list ->
                                messageSharedFlow.emit("Send")
                            }
                        }

                        is ResultData.Error -> {
                            it.onError { error ->
                                Log.d("ChatScreen", "error -> ${error.message}")
                                error.message?.let { it1 -> errorSharedFlow.emit(it1) }
                            }
                        }

                        is ResultData.Message -> {

                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                messageSharedFlow.emit("No Internet Connection")
            }
        }
    }
}