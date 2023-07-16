package uz.gita.dima.chat_app.presenter.screen.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.domain.usecase.AuthUseCase
import uz.gita.dima.chat_app.utils.ResultData
import uz.gita.dima.chat_app.utils.hasConnection
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sharedPref: SharedPref,
    private val direction: MainScreenDirection
): MainScreenViewModel, ViewModel(){

    override val loadingSharedFlow = MutableSharedFlow<Boolean>()

    override val messageSharedFlow = MutableSharedFlow<String>()

    override val errorSharedFlow = MutableSharedFlow<String>()

    override val allUser = MutableLiveData<List<User>>()

    override fun getAllUsers() {
        viewModelScope.launch {
            if (hasConnection()) {
                authUseCase.getAllUsers().onEach {
                    Log.d("TTT","ViewModel list -> kirdi")
                    when(it) {
                        is ResultData.Success -> {
                            it.onSuccess { list ->
                                Log.d("TTT","ViewModel list -> $list")
                                allUser.value = list
                            }
                        }

                        is ResultData.Message -> {
                            Log.d("TTT","ViewModel list -> Message")
                            it.onMessage { errorMessage ->
                                messageSharedFlow.emit(errorMessage)
                            }
                        }

                        is ResultData.Error -> {
                            Log.d("TTT","ViewModel list -> Errorg'a tusti")
                            // Error joq
                        }
                    }
                }.launchIn(viewModelScope)

            } else {
                messageSharedFlow.emit("No Internet Connection")
            }
        }
    }

    override fun goChatScreen(user: User) {
        viewModelScope.launch {
            direction.goChatScreen(user)
        }
    }

    override fun logOut() {
        sharedPref.isLogged = false
        viewModelScope.launch {
            direction.goSplash()
        }
    }
}