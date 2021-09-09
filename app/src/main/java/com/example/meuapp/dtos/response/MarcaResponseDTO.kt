package com.example.meuapp.dtos.response

import com.google.gson.annotations.SerializedName

data class MarcaResponseDTO(
    @SerializedName("idMarca")
    val idMarca: Long,
    @SerializedName("nome")
    val nome: String,
) {
    override fun toString(): String {
        return nome;
    }
}
