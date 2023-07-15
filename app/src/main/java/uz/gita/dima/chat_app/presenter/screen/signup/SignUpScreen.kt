package uz.gita.dima.chat_app.presenter.screen.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.databinding.ScreenSignUpBinding
import uz.gita.dima.chat_app.utils.*

@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_sign_up) {
    private val binding: ScreenSignUpBinding by viewBinding()
    private val viewModel: SignUpViewModel by viewModels<SignUpViewModelImp>()

    private var boolName = false
    private var boolEmail = false
    private var boolPassword = false

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

        inputName.textChanges().onEach {
            boolName = it.isNotEmpty()
            check()
        }.launchIn(lifecycleScope)


        inputEmail.textChanges().onEach {
            boolEmail = it.isNotEmpty()
            check()
        }.launchIn(lifecycleScope)

        inputPassword.textChanges().onEach {
            boolPassword = it.isNotEmpty()
            check()
        }.launchIn(lifecycleScope)

        btnSignIn.clicks()
            .onEach {
                if (boolName && boolEmail && boolPassword) {
                    val name = inputName.text.toString()
                    val email = inputEmail.text.toString()
                    val password = inputPassword.text.toString()
                    viewModel.signUp(name,email, password)
                }
            }.launchIn(lifecycleScope)
    }

    private fun check() = binding.include {
        btnSignIn.apply {
            isEnabled = boolName && boolEmail && boolPassword
        }
    }
}