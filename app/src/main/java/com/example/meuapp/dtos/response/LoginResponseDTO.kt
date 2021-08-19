package com.example.meuapp.dtos.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("response")
    val response: Boolean
)
