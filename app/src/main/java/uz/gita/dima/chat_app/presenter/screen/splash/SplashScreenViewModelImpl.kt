package uz.gita.dima.chat_app.presenter.screen.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.utils.hasConnection
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModelImpl @Inject constructor(
    private val sharedPref: SharedPref,
    private val direction: SplashScreenDirection
) : ViewModel(), SplashScreenViewModel {
    override val hasConnection = MutableSharedFlow<Boolean>()

    override fun findScreen() {
        if (hasConnection()) {
            viewModelScope.launch {
                hasConnection.emit(true)
            }
            if (sharedPref.isLogged) {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModelScope.launch {
                        direction.navigateToMain()
                    }
                }, 2000)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModelScope.launch {
                        direction.navigateToLogin()
                    }
                }, 2000)
            }

        } else {
            viewModelScope.launch {
                hasConnection.emit(false)
            }
        }
    }
}