package uz.gita.dima.chat_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.chat_app.presenter.screen.chat.ChatScreenDirection
import uz.gita.dima.chat_app.presenter.screen.chat.ChatScreenDirectionImp
import uz.gita.dima.chat_app.presenter.screen.login.LoginScreenDirection
import uz.gita.dima.chat_app.presenter.screen.login.LoginScreenDirectionImpl
import uz.gita.dima.chat_app.presenter.screen.login.LoginScreenDirections
import uz.gita.dima.chat_app.presenter.screen.main.MainScreenDirection
import uz.gita.dima.chat_app.presenter.screen.main.MainScreenDirectionImpl
import uz.gita.dima.chat_app.presenter.screen.signup.SignUpScreenDirection
import uz.gita.dima.chat_app.presenter.screen.signup.SignUpScreenDirectionImpl
import uz.gita.dima.chat_app.presenter.screen.splash.SplashScreenDirection
import uz.gita.dima.chat_app.presenter.screen.splash.SplashScreenDirectionImpl

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {

    @Binds
    fun bindSignUpScreen(impl: SignUpScreenDirectionImpl): SignUpScreenDirection

    @Binds
    fun bindSplashScreenDirection(impl: SplashScreenDirectionImpl): SplashScreenDirection

    @Binds
    fun bindLoginScreenDirection(impl: LoginScreenDirectionImpl): LoginScreenDirection

    @Binds
    fun bindMainScreenDirection(impl: MainScreenDirectionImpl): MainScreenDirection

    @Binds
    fun bindChatScreenDirection(impl: ChatScreenDirectionImp): ChatScreenDirection
}