package uz.gita.dima.chat_app.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.gita.dima.chat_app.MainActivity
import uz.gita.dima.chat_app.presenter.dialogs.ErrorDialog
import uz.gita.dima.chat_app.presenter.dialogs.MessageDialog

fun Fragment.hideProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.showProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.showErrorDialog(message: String) {
    val dialog = ErrorDialog(requireContext(), message)
    dialog.show()
}

fun Fragment.showMessageDialog(message: String) {
    val dialog = MessageDialog(requireContext(), message)
    dialog.show()

}


fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}


