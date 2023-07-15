package uz.gita.dima.chat_app.presenter.screen.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.databinding.ScreenSplashBinding

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen: Fragment(R.layout.screen_splash) {

    private val binding: ScreenSplashBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}