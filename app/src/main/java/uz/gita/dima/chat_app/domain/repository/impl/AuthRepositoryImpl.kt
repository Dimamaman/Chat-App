package uz.gita.dima.chat_app.domain.repository.impl

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.domain.repository.AuthRepository
import uz.gita.dima.chat_app.utils.ResultData
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPref
) : AuthRepository {
    private val mAuth = FirebaseAuth.getInstance()

    override fun signUp(name: String, email: String, password: String): Flow<ResultData<String>> =
        callbackFlow {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sharedPref.uuid = mAuth.currentUser?.uid
                        trySend(ResultData.Success("Successfully Signed"))
                    }
                }
                .addOnFailureListener {
                    trySend(ResultData.Error(it))
                }
            awaitClose()
        }

    override fun login(email: String, password: String): Flow<ResultData<String>> = callbackFlow {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = mAuth.currentUser
                    sharedPref.uuid = user?.uid
                    trySend(ResultData.Success("Successfully Logged"))
                }
            }.addOnFailureListener {
                trySend(ResultData.Error(it))
            }

        awaitClose()
    }

}