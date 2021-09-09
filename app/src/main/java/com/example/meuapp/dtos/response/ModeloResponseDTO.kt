package com.example.meuapp.dtos.response

import com.google.gson.annotations.SerializedName

data class ModeloResponseDTO(
    @SerializedName("idModelo")
    val idModelo: Long,
    @SerializedName("nomeModelo")
    val nomeModelo: String,
    @SerializedName("idMarca")
    val idMarca: Long,
    @SerializedName("nomeMarca")
    val nomeMarca: String
)
