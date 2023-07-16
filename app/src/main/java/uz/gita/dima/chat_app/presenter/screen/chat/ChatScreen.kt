package uz.gita.dima.chat_app.presenter.screen.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.databinding.ScreenChatBinding
import uz.gita.dima.chat_app.presenter.adapter.MessageAdapter
import uz.gita.dima.chat_app.utils.include

@AndroidEntryPoint
class ChatScreen : Fragment(R.layout.screen_chat) {

    private val binding: ScreenChatBinding by viewBinding()

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    private lateinit var mDbRef: DatabaseReference

    private var receiver: String? = null
    private var sender: String? = null

    private val args: ChatScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.include {

        mDbRef = FirebaseDatabase.getInstance().reference

        val name = args.user.name
        binding.messageReceiverName.text = name
        val receiverUID = args.user.uid
        val senderUID = FirebaseAuth.getInstance().currentUser?.uid

        sender = receiverUID + senderUID
        receiver = senderUID + receiverUID

        messageList = ArrayList()
        messageAdapter = MessageAdapter(requireContext(), messageList)

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