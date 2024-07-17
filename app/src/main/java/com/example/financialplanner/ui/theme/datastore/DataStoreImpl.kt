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

    //writing data
    override suspend fun saveUser(userModel: UserModel) {
        val userModelJson = gson.toJson(userModel)
        try {
            dataStore.edit { preference ->
                preference[USER_MODEL_KEY] = userModelJson
            }
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }

    }

    //reading data
    override suspend fun getUser(): Flow<UserModel> {
//        val userFlow: Flow<UserModel> = flow {
//            if()
//            dataStore.data.collect { preference ->
//                val user = preference[USER_MODEL_KEY] ?: ""
//                val decode = gson.fromJson(user, UserModel::class.java)
//                emit(decode)
//            }
//        }
//        return userFlow
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

    //delete existing user
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



