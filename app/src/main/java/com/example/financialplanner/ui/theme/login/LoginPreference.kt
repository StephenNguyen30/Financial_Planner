package com.example.financialplanner.ui.theme.login

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "login_prefs")

class LoginPreference (
    context: Context
){
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey(name = "is_logged_in")
    }
    private val applicationContext = context.applicationContext

    val isLoggedIn: Flow<Boolean> = applicationContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }

    suspend fun setLoginState(isLoggedIn: Boolean) {
        applicationContext.dataStore.edit { preferences->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }
}