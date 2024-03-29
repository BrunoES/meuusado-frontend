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
    @SerializedName("ano")
    val ano: Int,
    @SerializedName("valor")
    val valor: Float,
    @SerializedName("base64Imagem")
    val base64Imagem: String,
    @SerializedName("listAnuncioFotosBase64")
    var listAnuncioFotosBase64: ArrayList<String>,
    @SerializedName("coordinates")
    val coordinates: String
)
