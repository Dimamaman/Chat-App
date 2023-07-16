package uz.gita.dima.chat_app

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dima.chat_app.app.App
import uz.gita.dima.chat_app.databinding.ActivityMainBinding
import uz.gita.dima.chat_app.navigation.NavigationHandler
import uz.gita.dima.chat_app.presenter.dialogs.ProgressDialog
import uz.gita.dima.chat_app.utils.connection.ConnectivityObserver
import uz.gita.dima.chat_app.utils.connection.NetworkConnectivityObserver
import uz.gita.dima.chat_app.utils.gone
import uz.gita.dima.chat_app.utils.hasConnection
import uz.gita.dima.chat_app.utils.visible
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var connectivityObserver: ConnectivityObserver

    @Inject
    lateinit var navigationHandler: NavigationHandler

    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        internetAvailable()


        /*connectivityObserver = NetworkConnectivityObserver(App.instance.applicationContext)

        lifecycleScope.launch {
            connectivityObserver.observe().collect() {
                when(it) {
                    ConnectivityObserver.Status.Available -> {
                        recreate()
                        binding.noInternet.gone()
//                        internetAvailable()
                    }

                    ConnectivityObserver.Status.Unavailable -> {
                        Log.d("TTT","Internet -> ${it.name}")
                        binding.apply {
                            fragmentContainerView.gone()
                            noInternet.visible()
                        }
                    }

                    ConnectivityObserver.Status.Lost -> {
                        Log.d("TTT","Internet -> ${it.name}")
                        binding.apply {
                            fragmentContainerView.gone()
                            noInternet.visible()
                        }
                    }

                    ConnectivityObserver.Status.Losing -> {
                        binding.apply {
                            fragmentContainerView.gone()
                            noInternet.visible()
                        }
                        Log.d("TTT","Internet -> ${it.name}")
                    }
                }
            }
        }*/

        dialog = ProgressDialog(this)

    }

    private fun internetAvailable() {
        if (hasConnection()) {
            binding.fragmentContainerView.visible()
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navigationHandler.navigationStack
                .onEach { it.invoke(fragment.findNavController()) }
                .launchIn(lifecycleScope)
            binding.noInternet.gone()
        } else {
            binding.apply {
                fragmentContainerView.gone()
                noInternet.visible()
            }
        }
    }

    fun showProgress() {
        dialog.show()
    }

    fun hideProgress() {
        dialog.cancel()
    }
}