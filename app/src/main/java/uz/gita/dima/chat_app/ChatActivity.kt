package uz.gita.dima.chat_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.gita.dima.chat_app.presenter.adapter.MessageAdapter
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    private var receiver: String? = null
    private var sender: String? = null

    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDbRef = FirebaseDatabase.getInstance().reference

        val name = intent.getStringExtra("name")
        binding.messageReceiverName.text = name
        val receiverUID = intent.getStringExtra("uid")
        val senderUID = FirebaseAuth.getInstance().currentUser?.uid

        sender = receiverUID + senderUID
        receiver = senderUID + receiverUID

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        binding.chatRecyclerview.adapter = messageAdapter

        mDbRef.child("chats").child(sender!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        binding.bntSend.setOnClickListener {
            val message = binding.inputMessage.text.toString()
            val messageObject = Message(message, senderUID)

            mDbRef.child("chats").child(sender!!).child("messages").push().setValue(messageObject)
                .addOnSuccessListener {
                    mDbRef.child("chats").child(receiver!!).child("messages").push()
                        .setValue(messageObject)
                }

            binding.inputMessage.text?.clear()
        }
    }
}