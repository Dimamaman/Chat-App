package uz.gita.dima.chat_app.presenter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.databinding.ReceiverBinding
import uz.gita.dima.chat_app.databinding.SentBinding
import uz.gita.dima.chat_app.utils.inflate
import javax.inject.Inject

class MessageAdapter @Inject constructor() : ListAdapter<Message, RecyclerView.ViewHolder>(messageItemCallback) {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    inner class SendViewHolder(binding: SentBinding): RecyclerView.ViewHolder(binding.root) {
        val sentMessage = binding.sentMessage
    }

    inner class ReceiveViewHolder(binding: ReceiverBinding): RecyclerView.ViewHolder(binding.root) {
        val receiveMessage = binding.receiveMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            ReceiveViewHolder(ReceiverBinding.bind(parent.inflate(R.layout.receiver)))
        } else {
            SendViewHolder(SentBinding.bind(parent.inflate(R.layout.sent)))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = getItem(position)

        if (holder.javaClass == SendViewHolder::class.java) {
            val viewHolder = holder as SendViewHolder
            holder.sentMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as MessageAdapter.ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = getItem(position)
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }
}

private var messageItemCallback = object : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
        oldItem.message == newItem.message && oldItem.senderId == newItem.senderId
}














