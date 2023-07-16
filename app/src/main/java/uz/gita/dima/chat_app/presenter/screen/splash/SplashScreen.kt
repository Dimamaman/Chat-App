package uz.gita.dima.chat_app.presenter.screen.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.app.App
import uz.gita.dima.chat_app.databinding.ScreenSplashBinding
import uz.gita.dima.chat_app.utils.connection.ConnectivityObserver
import uz.gita.dima.chat_app.utils.connection.NetworkConnectivityObserver
import uz.gita.dima.chat_app.utils.gone
import uz.gita.dima.chat_app.utils.visible

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen: Fragment(R.layout.screen_splash) {

    private val binding: ScreenSplashBinding by viewBinding()
    private val viewModel: SplashScreenViewModel by viewModels<SplashScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TTT","Splash Blat -> ")
        viewModel.findScreen()

        viewModel.hasConnection.onEach {
            if (it) {
                Log.d("TTT","Internet -> $it")
                binding.noInternet.gone()
            } else {
                Log.d("TTT","Internet -> $it")
                binding.noInternet.visible()
            }
        }.launchIn(lifecycleScope)
    }
}