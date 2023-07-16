package uz.gita.dima.chat_app.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.utils.ResultData

interface AuthUseCase {

    fun signUp(name: String, email: String, password: String): Flow<ResultData<String>>

    fun login(email: String, password: String): Flow<ResultData<String>>

    fun getAllUsers() : Flow<ResultData<List<User>>>

    fun getAllMessagesBySender(sender: String) : Flow<ResultData<List<Message>>>

    fun sendMessage(sender: String, receiver: String, messageObject: Message): Flow<ResultData<Unit>>

}