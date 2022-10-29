package com.example.meuapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class LocationUtils {
    companion object {

        fun getLatitude(actvity : Activity) : String {
            val sharedPreferences : SharedPreferences = actvity.getSharedPreferences("meuUsadoPref", Context.MODE_PRIVATE)
            return sharedPreferences.getString("latitude", "0.0").toString()
        }

        fun getLongitude(actvity : Activity) : String {
            val sharedPreferences : SharedPreferences = actvity.getSharedPreferences("meuUsadoPref", Context.MODE_PRIVATE)
            return sharedPreferences.getString("longitude", "0.0").toString()
        }

        fun format(latitude : String, longitude : String) : String {
            return latitude + "," + longitude;
        }

        fun getCoordinates(actvity : Activity) : String {
            return format(getLatitude(actvity), getLongitude(actvity))
        }
    }
}