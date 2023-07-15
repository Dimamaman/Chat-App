package uz.gita.dima.chat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.gita.dima.chat_app.data.User
import uz.gita.dima.chat_app.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            signUp(name, email, password)
        }
    }


    private fun signUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    addToDatabaseUser(name, email, password, mAuth.currentUser!!.uid)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun addToDatabaseUser(name: String, email: String, password: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("users").child(uid).setValue(User(name, email, password, uid))
    }
}