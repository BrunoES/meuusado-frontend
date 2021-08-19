package com.example.meuapp.recicleviews.items

import android.graphics.drawable.Drawable

class AnuncioItem {
    var titulo: String = ""
    var imageContent: String = ""

    constructor(titulo: String, imageContent: String?) {
        this.titulo = titulo
        if (imageContent != null) {
            this.imageContent = imageContent
        }
    }
}