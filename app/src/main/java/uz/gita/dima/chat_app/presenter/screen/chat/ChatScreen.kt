package uz.gita.dima.chat_app.presenter.screen.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.databinding.ScreenChatBinding
import uz.gita.dima.chat_app.presenter.adapter.MessageAdapter
import uz.gita.dima.chat_app.utils.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatScreen : Fragment(R.layout.screen_chat) {

    private val binding: ScreenChatBinding by viewBinding()
    private val viewModel: ChatScreenViewModel by viewModels<ChatScreenViewModelImp>()

    @Inject
    lateinit var messageAdapter: MessageAdapter

    private var receiver: String? = null
    private var sender: String? = null

    private val args: ChatScreenArgs by navArgs()

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.include {
        val name = args.user.name
        binding.messageReceiverName.text = name

        val receiverUID = args.user.uid
        val senderUID = FirebaseAuth.getInstance().currentUser?.uid

        sender = receiverUID + senderUID
        receiver = senderUID + receiverUID

        viewModel.getMessagesBySender(sender!!)
        viewModel.allMessage.observe(viewLifecycleOwner, allMessageList)

        chatRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        chatRecyclerview.adapter = messageAdapter

        viewModel.loadingSharedFlow.onEach {
            if (it) showProgress() else hideProgress()
        }.launchIn(lifecycleScope)

        viewModel.errorSharedFlow.onEach {
            showErrorDialog(it)
        }.launchIn(lifecycleScope)

        viewModel.messageSharedFlow.onEach {
//            showMessageDialog(it)
        }.launchIn(lifecycleScope)

        bntSend.setOnClickListener {
            val message = inputMessage.text.toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val messageObject = Message(message, senderUID, currentDate)

            viewModel.sendMessage(sender!!, receiver!!, messageObject)
            binding.inputMessage.text?.clear()
            hideKeyboard()
        }

        btnBack.setOnClickListener {
            viewModel.back()
        }
    }

    private val allMessageList = Observer<List<Message>> {data ->
        Log.d("ChatScreen","curList size -> ${messageAdapter.currentList.size}")
        Log.d("ChatScreen","new list size -> ${data.size}")

        messageAdapter.submitList(data)
        messageAdapter.notifyDataSetChanged()
    }
}