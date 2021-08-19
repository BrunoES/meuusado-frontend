package com.example.meuapp.dtos.response

import com.google.gson.annotations.SerializedName

data class UsuarioResponseDTO(
    @SerializedName("idUsuario")
    val idUsuario: Long,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String)
