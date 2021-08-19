package com.example.meuapp.dtos.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class AnuncioResumidoResponseDTO(
    @SerializedName("idAnuncio")
    val idAnuncio: Long,
    @SerializedName("idUsuario")
    val idUsuario: Long,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("valor")
    val valor: Float,
    @SerializedName("dataCriacao")
    val dataCriacao: Date,
    @SerializedName("base64Imagem")
    val base64Imagem: String,
    @SerializedName("base64ImgPrincMin")
    val base64ImgPrincMin: String
)
