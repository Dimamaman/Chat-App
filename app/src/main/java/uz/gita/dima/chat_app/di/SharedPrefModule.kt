package uz.gita.dima.chat_app.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.chat_app.data.local.sharedPref.SharedPref
import uz.gita.dima.chat_app.data.local.sharedPref.impl.SharedPrefImpl
import uz.gita.dima.chat_app.utils.Constant.SHARED_PREF
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {
    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideMySharedPref(sh: SharedPreferences): SharedPref = SharedPrefImpl(sh)
}