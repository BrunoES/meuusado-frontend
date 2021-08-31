package com.example.meuapp.dtos.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class AnuncioResponseDTO(
    @SerializedName("idAnuncio")
    val idAnuncio: Long,
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
    @SerializedName("dataCriacao")
    val dataCriacao: Date,
    @SerializedName("base64Imagem")
    val base64Imagem: String
)
