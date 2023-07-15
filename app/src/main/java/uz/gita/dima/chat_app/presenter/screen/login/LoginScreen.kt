package uz.gita.dima.chat_app.presenter.screen.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.chat_app.MainActivity
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.databinding.ScreenLoginBinding
import uz.gita.dima.chat_app.utils.include


@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.activity_log_in) {

    private val binding: ScreenLoginBinding by viewBinding()

    private lateinit var mAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.include {
        mAuth = FirebaseAuth.getInstance()
    }


    private fun logIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Log.d("TTT", "Current User -> $user")
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
                } else {
                    Log.w("TTT", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}