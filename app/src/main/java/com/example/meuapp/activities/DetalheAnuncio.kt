package com.example.meuapp.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.meuapp.MainActivity
import com.example.meuapp.R
import com.example.meuapp.dtos.request.CadastroUsuarioDTO
import com.example.meuapp.dtos.response.AnuncioResponseDTO
import com.example.meuapp.dtos.response.UsuarioResponseDTO
import com.example.meuapp.utils.Retrofit2Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetalheAnuncio : AppCompatActivity() {

    lateinit var valor: TextView
    lateinit var titulo: TextView
    lateinit var descricao: TextView
    lateinit var image_view: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_anuncio)

        valor = findViewById(R.id.txt_valor_detalhe_anuncio)
        titulo = findViewById(R.id.txt_titulo_detalhe_anuncio)
        descricao = findViewById(R.id.txt_descricao_detalhe_anuncio)
        image_view = findViewById(R.id.img_detalhe_anuncio)

        val anuncioId = intent.getStringExtra("anuncio_id")

        buscaDetalhesAnuncio(Integer.parseInt(anuncioId))
    }

    private fun buscaDetalhesAnuncio(anuncioId: Int) {

        Toast.makeText(applicationContext, "Chamou API de busca de detalhes de Anúncio 1", Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()

        Toast.makeText(applicationContext, "Chamou API de busca de detalhes de Anúncio ", Toast.LENGTH_LONG).show()

        val retrofitData = retrofitBuilder.buscaDetalhesAnuncio(anuncioId)

        println("LogInfoInicioDetalhe")
        println(retrofitData.request().url())
        println(retrofitData.request().method())
        println(retrofitData.request().headers())
        println(retrofitData.request().body())
        println("LogInfoFimDetalhe")

        retrofitData.enqueue(
            object : Callback<AnuncioResponseDTO> {
                override fun onFailure(call: Call<AnuncioResponseDTO>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de detalhes de Anúncio", Toast.LENGTH_LONG).show()
                }
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<AnuncioResponseDTO>, response: Response<AnuncioResponseDTO>) {
                    var response = response.body()
                    if (response != null) {
                        valor.setText(response.valor.toString())
                        titulo.setText(response.titulo)
                        descricao.setText(response.descricao)

                        var byteImage : ByteArray? = Base64.getDecoder().decode(response.base64Imagem)
                        var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size) })

                        image_view.setImageDrawable(image)
                    }
                }
            }
        )
    }

}