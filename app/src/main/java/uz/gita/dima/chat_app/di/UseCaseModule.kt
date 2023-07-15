package uz.gita.dima.chat_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.chat_app.domain.usecase.AuthUseCase
import uz.gita.dima.chat_app.domain.usecase.imp.AuthUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindAuthUseCase(impl: AuthUseCaseImpl): AuthUseCase
}