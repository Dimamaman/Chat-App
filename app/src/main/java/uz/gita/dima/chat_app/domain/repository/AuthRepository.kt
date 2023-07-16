package uz.gita.dima.chat_app.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.dima.chat_app.utils.ResultData

interface AuthRepository {

    fun signUp(name: String, email: String, password: String): Flow<ResultData<String>>

    fun login(email: String, password: String): Flow<ResultData<String>>
}