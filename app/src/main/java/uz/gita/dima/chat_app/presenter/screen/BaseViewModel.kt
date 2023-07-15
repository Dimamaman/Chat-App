package uz.gita.dima.chat_app.presenter.screen

import kotlinx.coroutines.flow.SharedFlow

interface BaseViewModel {

    val loadingSharedFlow: SharedFlow<Boolean>

    val messageSharedFlow: SharedFlow<String>

    val errorSharedFlow: SharedFlow<String>

}