package uz.gita.dima.chat_app.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.dima.chat_app.utils.ResultData

interface AuthUseCase {

    fun signUp(name: String, email: String, password: String): Flow<ResultData<String>>

    fun login(email: String, password: String): Flow<ResultData<String>>

}