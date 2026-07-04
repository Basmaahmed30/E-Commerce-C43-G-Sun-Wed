package com.route.data.dataSource.auth.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.route.data.dataSource.DataStoreKeys
import com.route.domain.model.Result
import com.route.domain.model.auth.AuthUser
import com.route.domain.repository.AuthLocalDataSource
import com.route.domain.utils.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AuthLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthLocalDataSource {
    override suspend fun saveToken(params: String): Flow<Result<Unit>> {
        return flow {
            try {
                dataStore.updateData {
                    it.toMutablePreferences().also { preferences ->
                        preferences[DataStoreKeys.TOKEN] = params
                    }
                }
                Log.e("TAG", "saveToken: Success !")
                emit(Result.Success())
            } catch (e: Exception) {
                Log.e("TAG", "saveToken: Error : ${e.message} !")
                emit(Result.Error(Failure.CustomException(e.message)))
            }
        }
    }

    override suspend fun getToken(): Flow<Result<String>> {
        return dataStore.data.map { preferences ->
            try {
                val token = preferences[DataStoreKeys.TOKEN] ?: ""
                Result.Success(token)
            } catch (e: Exception) {
                Result.Error(Failure.CustomException(e.message))
            }
        }
    }

    override suspend fun saveUser(user: AuthUser, password: String?): Flow<Result<Unit>> {
        return flow {
            try {
                dataStore.updateData {
                    it.toMutablePreferences().also { preferences ->
                        preferences[DataStoreKeys.USER_NAME] = user.name ?: ""
                        preferences[DataStoreKeys.USER_EMAIL] = user.email ?: ""
                        if (password != null) {
                            preferences[DataStoreKeys.USER_PASSWORD] = password
                        }
                    }
                }
                emit(Result.Success())
            } catch (e: Exception) {
                emit(Result.Error(Failure.CustomException(e.message)))
            }
        }
    }

    override suspend fun getUser(): Flow<Result<Pair<AuthUser, String>>> {
        return dataStore.data.map { preferences ->
            try {
                val name = preferences[DataStoreKeys.USER_NAME] ?: ""
                val email = preferences[DataStoreKeys.USER_EMAIL] ?: ""
                val password = preferences[DataStoreKeys.USER_PASSWORD] ?: ""
                Result.Success(AuthUser(name = name, email = email) to password)
            } catch (e: Exception) {
                Result.Error(Failure.CustomException(e.message))
            }
        }
    }

}
