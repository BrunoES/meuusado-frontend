package com.example.meuapp.recicleviews.items

class AnuncioItem {
    var idAnuncio: Long = 0L
    var titulo: String = ""
    var imageContent: String = ""
    var valor: Float = 0.0F

    constructor(idAnuncio: Long, titulo: String, imageContent: String?, valor: Float?) {
        this.idAnuncio = idAnuncio
        this.titulo = titulo
        if (imageContent != null) {
            this.imageContent = imageContent
        }
        if (valor != null) {
            this.valor = valor
        }
    }
}