package uz.gita.dima.chat_app.presenter.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.app.App
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.domain.usecase.AuthUseCase
import uz.gita.dima.chat_app.utils.ResultData
import uz.gita.dima.chat_app.utils.hasConnection
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModelImpl @Inject constructor(
    private val sharedPref: SharedPref,
    private val authUseCase: AuthUseCase,
    private val direction: LoginScreenDirection

) : LoginScreenViewModel, ViewModel() {

    override val loadingSharedFlow = MutableSharedFlow<Boolean>()
    override val messageSharedFlow = MutableSharedFlow<String>()
    override val errorSharedFlow = MutableSharedFlow<String>()

    override fun login(email: String, password: String) {
        viewModelScope.launch {
            if (hasConnection()) {
                loadingSharedFlow.emit(true)
                if (sharedPref.email == email && sharedPref.password == password) {
                    direction.navigateToMain()
                } else {
                    authUseCase.login(email, password).collectLatest {
                        when(it) {
                            is ResultData.Success -> {
                                it.onSuccess { success ->
                                    loadingSharedFlow.emit(false)
                                    sharedPref.apply {
                                        this.name = name
                                        this.email = email
                                        this.password = password
                                        this.isLogged = true
                                    }
                                    Log.d("TTT","Success -> $success")
                                    messageSharedFlow.emit(success)
                                    direction.navigateToMain()
                                }
                            }

                            is ResultData.Message -> {
                                it.onMessage {
                                    messageSharedFlow.emit(it)
                                }
                            }

                            is ResultData.Error -> {
                                it.onError {
                                    loadingSharedFlow.emit(false)
                                    it.message?.let { it1 -> messageSharedFlow.emit(App.instance.applicationContext.getString(R.string.errorMessage)) }
                                }
                            }
                        }
                    }
                }
            } else {
                loadingSharedFlow.emit(false)
                errorSharedFlow.emit("No Internet Connection")
            }
        }
    }

    override fun navigateToSignUp() {
        viewModelScope.launch {
            direction.navigateToSignUp()
        }
    }
}