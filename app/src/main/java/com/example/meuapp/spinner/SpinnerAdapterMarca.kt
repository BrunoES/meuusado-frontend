package com.example.meuapp.spinner

import android.content.Context
import android.widget.ArrayAdapter
import com.example.meuapp.dtos.response.MarcaResponseDTO

// https://stackoverflow.com/questions/1625249/android-how-to-bind-spinner-to-custom-object-list

class SpinnerAdapterMarca(context: Context, resource: Int) :
    ArrayAdapter<MarcaResponseDTO>(context, resource) {

    override fun getCount(): Int {
        return 0;
    }
}