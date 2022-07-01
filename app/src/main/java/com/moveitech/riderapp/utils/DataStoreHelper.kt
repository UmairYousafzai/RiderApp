package com.moveitech.riderapp.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.moveitech.riderapp.dataModel.login.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreHelper @Inject constructor(private val dataStore:DataStore<Preferences>){


    val isLogin:Flow<Boolean> = dataStore.data.map { preferences->
        preferences[IS_LOGIN]?:false
    }

    val isServiceRunning:Flow<Boolean> = dataStore.data.map { preferences->
        preferences[IS_SERVICE_RUNNING]?:false
    }
    val orderNum:Flow<String> = dataStore.data.map { preferences->
        preferences[ORDER_NUM]?:""
    }


    suspend fun saveOrderNum(orderNum:String){
        dataStore.edit { preferences->
            preferences[ORDER_NUM]=orderNum
        }
    }
    suspend fun saveIsLogin(isLogin:Boolean){
        dataStore.edit { preferences->
            preferences[IS_LOGIN]=isLogin
        }
    }
    suspend fun saveUser(user:User){
        dataStore.edit { preferences->
            preferences[RIDER_CODE]=user.RiderCode
            preferences[RIDER_NAME]=user.RiderName
        }
    }

    val user:Flow<User> = dataStore.data.map { preferences->
        User(
            RiderCode = preferences[RIDER_CODE]?:"",
            RiderName = preferences[RIDER_NAME]?:""
        )
    }
    suspend fun saveIsServiceRunning(isServiceRunning:Boolean){
        dataStore.edit { preferences->
            preferences[IS_SERVICE_RUNNING]=isServiceRunning
        }
    }

    suspend fun clear(){
        dataStore.edit {
            it.clear()


        }
    }

    companion object {
        const val DATA_STORE_NAME="attendance_and_track_datastore"
        val IS_LOGIN= booleanPreferencesKey(name = "isLogin")
        val RIDER_CODE= stringPreferencesKey(name = "rider code")
        val RIDER_NAME= stringPreferencesKey(name = "rider name")

        val IS_SERVICE_RUNNING= booleanPreferencesKey(name = "isServiceRunning")
        val ORDER_NUM= stringPreferencesKey(name = "order_num")
    }

}


