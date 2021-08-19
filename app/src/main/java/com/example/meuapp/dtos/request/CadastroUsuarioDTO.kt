package com.example.meuapp.dtos.request

import com.google.gson.annotations.SerializedName

data class CadastroUsuarioDTO(
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)