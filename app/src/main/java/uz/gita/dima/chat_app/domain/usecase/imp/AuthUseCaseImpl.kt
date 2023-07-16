package uz.gita.dima.chat_app.domain.usecase.imp

import kotlinx.coroutines.flow.Flow
import uz.gita.dima.chat_app.data.common.Message
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.domain.repository.AuthRepository
import uz.gita.dima.chat_app.domain.usecase.AuthUseCase
import uz.gita.dima.chat_app.utils.ResultData
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): AuthUseCase {
    override fun signUp(name: String, email: String, password: String): Flow<ResultData<String>> =
        authRepository.signUp(name, email, password)

    override fun login(email: String, password: String): Flow<ResultData<String>> =
        authRepository.login(email, password)

    override fun getAllUsers(): Flow<ResultData<List<User>>> = authRepository.getAllUsers()

    override fun getAllMessagesBySender(sender: String): Flow<ResultData<List<Message>>> =
        authRepository.getAllMessagesBySender(sender)

    override fun sendMessage(
        sender: String,
        receiver: String,
        messageObject: Message
    ): Flow<ResultData<Unit>> {
        return authRepository.sendMessage(sender, receiver, messageObject)
    }

}