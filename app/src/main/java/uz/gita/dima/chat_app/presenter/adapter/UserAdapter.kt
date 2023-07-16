package uz.gita.dima.chat_app.presenter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.databinding.UserItemBinding
import uz.gita.dima.chat_app.utils.include
import uz.gita.dima.chat_app.utils.inflate
import javax.inject.Inject

class UserAdapter @Inject constructor(): ListAdapter<User, UserAdapter.ViewHolder>(userItemCallback) {

    private var userClickListener: ((User) -> Unit)? = null

    fun setUserClickListener(l: (User) -> Unit) {
        userClickListener = l
    }

    inner class ViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                userClickListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        fun bind() = binding.include {
            val user = getItem(absoluteAdapterPosition)
            userName.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(UserItemBinding.bind(parent.inflate(R.layout.user_item)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}

private var userItemCallback = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.name == newItem.name &&
                oldItem.email == newItem.email &&
                oldItem.password == newItem.password &&
                oldItem.uid == newItem.uid

}

