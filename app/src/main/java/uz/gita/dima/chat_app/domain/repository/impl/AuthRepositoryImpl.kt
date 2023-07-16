package uz.gita.dima.chat_app.domain.repository.impl

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.domain.repository.AuthRepository
import uz.gita.dima.chat_app.utils.ResultData
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPref
) : AuthRepository {
    private val mAuth = FirebaseAuth.getInstance()
    private val mDbRef = FirebaseDatabase.getInstance().reference

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

    override fun getAllUsers(): Flow<ResultData<List<User>>> = callbackFlow {
        val userList = ArrayList<User>()
        mDbRef.child("users").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                        Log.d("TTT", "UserList Repo -> $userList")
                    }
                }
                trySend(ResultData.Success(userList))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultData.Message("No Users"))
            }
        })
        awaitClose()
    }

    override fun getAllMessagesBySender(sender: String): Flow<ResultData<List<Message>>> =
        callbackFlow {
            val messageList = ArrayList<Message>()
            mDbRef.child("chats").child(sender).child("messages")
                .addValueEventListener(object : ValueEventListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onDataChange(snapshot: DataSnapshot) {
                        messageList.clear()
                        for (postSnapshot in snapshot.children) {
                            val message = postSnapshot.getValue(Message::class.java)
                            messageList.add(message!!)
                        }
                        trySend(ResultData.Success(messageList))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        trySend(ResultData.Message("No Messages"))
                    }
                })
            awaitClose()
        }

    override fun sendMessage(
        sender: String,
        receiver: String,
        messageObject: Message
    ): Flow<ResultData<Unit>> = callbackFlow {

        mDbRef.child("chats").child(sender).child("messages").push().setValue(messageObject)
            .addOnSuccessListener {
                mDbRef.child("chats").child(receiver).child("messages").push()
                    .setValue(messageObject)
            }
            .addOnFailureListener {
                trySend(ResultData.Error(it))
            }
        trySend(ResultData.Success(Unit))
        awaitClose()
    }

}


//        binding.bntSend.setOnClickListener {
//            val message = binding.inputMessage.text.toString()
//            val messageObject = Message(message, senderUID)
//
//            mDbRef.child("chats").child(sender!!).child("messages").push().setValue(messageObject)
//                .addOnSuccessListener {
//                    mDbRef.child("chats").child(receiver!!).child("messages").push()
//                        .setValue(messageObject)
//                }
//
//            binding.inputMessage.text?.clear()
//        }



















