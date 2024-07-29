package com.example.financialplanner.ui.theme.datastore


import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.financialplanner.ui.theme.datastore.PreferenceKeys.USER_MODEL_KEY
import com.example.financialplanner.ui.theme.model.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStorePreference {
    private val gson = Gson()

    override suspend fun saveUser(userModel: UserModel) {
        val userModelJson = gson.toJson(userModel)
        try {
            dataStore.edit { preference ->
                preference[USER_MODEL_KEY] = userModelJson
            }
            Log.d("DataStore", "User saved successfully: $userModelJson")
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

    override suspend fun getUser(): Flow<UserModel> {
        return flow {
            dataStore.data.collect { preference ->
                val userJson = preference[USER_MODEL_KEY]
                if (!userJson.isNullOrEmpty()) {
                    val decode = gson.fromJson(userJson, UserModel::class.java)
                    emit(decode)
                } else {
                    emit(UserModel(""))
                }
            }
        }
    }

    override suspend fun deleteUser(userModel: UserModel) {
        dataStore.edit { preference ->
            preference.clear()
        }
    }

}

interface DataStorePreference {
    suspend fun saveUser(userModel: UserModel)
    suspend fun getUser(): Flow<UserModel>
    suspend fun deleteUser(userModel: UserModel)

}

object PreferenceKeys {
    val USER_MODEL_KEY = stringPreferencesKey("user_model")
}



