package com.example.financialplanner.ui.theme.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ) = context.datastore
}

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreBinds {

    @Binds
    @Singleton
    fun bindPreferenceDataStore(bind: DataStoreImpl): DataStorePreference
}
const val PREFERENCE_NAME = "app_prefs"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

