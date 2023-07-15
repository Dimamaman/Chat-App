package uz.gita.dima.chat_app.presenter.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import uz.gita.dima.chat_app.databinding.DialogErrorBinding
import uz.gita.dima.chat_app.utils.config

class ErrorDialog(ctx: Context, private val message: String) : Dialog(ctx) {

    private lateinit var binding: DialogErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogErrorBinding.inflate(layoutInflater)
        config(binding)
        binding.tvErrorMessage.text = message
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}