package com.example.meuapp.dtos.request

import com.google.gson.annotations.SerializedName

data class CadastroAnuncioDTO(
    @SerializedName("idUsuario")
    val idUsuario: Long,
    @SerializedName("idModelo")
    val idModelo: Long,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("valor")
    val valor: Float,
    @SerializedName("base64Imagem")
    val base64Imagem: String
)
