package com.example.meuapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class TokenUtils {
    companion object {

        fun getToken(actvity : Activity) : String {
            val sharedPreferences : SharedPreferences = actvity.getSharedPreferences("tokenPref", Context.MODE_PRIVATE)
            val token : String = sharedPreferences.getString("token", "").toString()
            return token
        }
    }
}