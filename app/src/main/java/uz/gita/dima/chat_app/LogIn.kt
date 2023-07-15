package uz.gita.dima.chat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import uz.gita.dima.chat_app.databinding.ActivityLogInBinding

class LogIn : AppCompatActivity() {
    private var _binding: ActivityLogInBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            logIn(email, password)
        }
    }

    private fun logIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Log.d("TTT", "Current User -> $user")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.w("TTT", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}








