package uz.gita.dima.chat_app.presenter.screen.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.databinding.ScreenLoginBinding
import uz.gita.dima.chat_app.utils.*


@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {

    private val binding: ScreenLoginBinding by viewBinding()
    private val viewModel: LoginScreenViewModel by viewModels<LoginScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.include {

        viewModel.loadingSharedFlow.onEach {
            if (it) showProgress() else hideProgress()
        }.launchIn(lifecycleScope)

        viewModel.errorSharedFlow.onEach {
            showErrorDialog(it)
        }.launchIn(lifecycleScope)

        viewModel.messageSharedFlow.onEach {
            showMessageDialog(it)
        }.launchIn(lifecycleScope)

        btnLogin.setOnClickListener {
            if (inputEmail.text.toString().isEmpty() && inputPassword.text.toString().isEmpty()) {
                toast("Please fill")
            } else {
                val email = inputEmail.text.toString()
                val password = inputPassword.text.toString()
                viewModel.login(email, password)
            }
        }

        btnSignUp.setOnClickListener {
            viewModel.navigateToSignUp()
        }
    }

}