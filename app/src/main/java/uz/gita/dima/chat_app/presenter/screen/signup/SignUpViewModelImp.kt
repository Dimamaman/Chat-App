package uz.gita.dima.chat_app.presenter.screen.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.domain.usecase.AuthUseCase
import uz.gita.dima.chat_app.utils.ResultData
import uz.gita.dima.chat_app.utils.hasConnection
import javax.inject.Inject

@HiltViewModel
class SignUpViewModelImp @Inject constructor(
    private val sharedPref: SharedPref,
    private val authUseCase: AuthUseCase
): ViewModel(), SignUpViewModel{

    override val loadingSharedFlow = MutableSharedFlow<Boolean>()

    override val messageSharedFlow = MutableSharedFlow<String>()

    override val errorSharedFlow = MutableSharedFlow<String>()

    override fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            if (hasConnection()) {
                loadingSharedFlow.emit(true)
                authUseCase.signUp(name, email, password).collectLatest {
                    when(it) {
                        is ResultData.Error -> {
                            it.error.message?.let { it1 -> errorSharedFlow.emit(it1) }
                        }

                        is ResultData.Success -> {
                            it.onSuccess { success ->
                                sharedPref.apply {
                                    this.name = name
                                    this.email = email
                                    this.password = password
                                }
                                Log.d("TTT","Success -> $success")
                                messageSharedFlow.emit(success)
                            }
                        }

                        is  ResultData.Message -> {

                        }
                    }
                }
            } else {
                messageSharedFlow.emit("No Internet Connection")
            }
        }
    }
}