package uz.gita.dima.chat_app.presenter.screen.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.databinding.ScreenMainBinding
import uz.gita.dima.chat_app.presenter.adapter.UserAdapter
import uz.gita.dima.chat_app.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main){

    @Inject
    lateinit var userAdapter: UserAdapter
    private val binding: ScreenMainBinding by viewBinding()
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.include {

        viewModel.getAllUsers()

        viewModel.loadingSharedFlow.onEach {
            if (it) showProgress() else hideProgress()
        }.launchIn(lifecycleScope)

        viewModel.errorSharedFlow.onEach {
            showErrorDialog(it)
        }.launchIn(lifecycleScope)

        viewModel.messageSharedFlow.onEach {
            showMessageDialog(it)
        }.launchIn(lifecycleScope)

        viewModel.allUser.observe(viewLifecycleOwner, allUsersObserver)

        recyclerview.adapter = userAdapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))

        userAdapter.setUserClickListener {
            viewModel.goChatScreen(it)
        }

        logOut.setOnClickListener {
            viewModel.logOut()
        }
    }

    private val allUsersObserver = Observer<List<User>> {
        userAdapter.submitList(it)
    }
}
