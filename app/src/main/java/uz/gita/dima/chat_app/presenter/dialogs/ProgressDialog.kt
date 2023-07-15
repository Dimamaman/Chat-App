package uz.gita.dima.chat_app.presenter.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.dima.chat_app.databinding.DialogProgressBinding

class ProgressDialog(ctx: Context): Dialog(ctx) {
    private lateinit var binding: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogProgressBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        setCancelable(false)
    }
}