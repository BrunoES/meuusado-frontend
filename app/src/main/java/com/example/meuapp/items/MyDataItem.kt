package com.example.meuapp.items

import com.google.gson.annotations.SerializedName

data class MyDataItem(
    @SerializedName("body")
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
