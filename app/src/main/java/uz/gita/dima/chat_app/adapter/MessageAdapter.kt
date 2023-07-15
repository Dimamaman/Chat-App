package uz.gita.dima.chat_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.data.Message

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.receiver, parent, false)
            ReceiveViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            SendViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if (holder.javaClass == SendViewHolder::class.java) {
            val viewHolder = holder as SendViewHolder
            holder.sentMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    inner class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<AppCompatTextView>(R.id.sentMessage)

    }

    inner class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<AppCompatTextView>(R.id.receiveMessage)
    }
}