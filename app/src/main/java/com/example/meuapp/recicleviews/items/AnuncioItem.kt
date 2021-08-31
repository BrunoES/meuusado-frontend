package com.example.meuapp.recicleviews.items

import android.graphics.drawable.Drawable

class AnuncioItem {
    var titulo: String = ""
    var imageContent: String = ""
    var valor: Float = 0.0F

    constructor(titulo: String, imageContent: String?, valor: Float?) {
        this.titulo = titulo
        if (imageContent != null) {
            this.imageContent = imageContent
        }
        if (valor != null) {
            this.valor = valor
        }
    }
}