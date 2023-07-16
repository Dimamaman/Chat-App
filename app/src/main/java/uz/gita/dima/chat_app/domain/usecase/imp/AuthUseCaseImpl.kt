package uz.gita.dima.chat_app.domain.usecase.imp

import kotlinx.coroutines.flow.Flow
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

}