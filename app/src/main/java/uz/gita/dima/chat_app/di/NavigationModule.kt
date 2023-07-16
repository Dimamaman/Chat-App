package uz.gita.dima.chat_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.chat_app.navigation.NavigationDispatcher
import uz.gita.dima.chat_app.navigation.NavigationHandler
import uz.gita.dima.chat_app.navigation.Navigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun navigator(dispatcher: NavigationDispatcher): Navigator

    @[Binds Singleton]
    fun navigatorHandler(dispatcher: NavigationDispatcher): NavigationHandler

}