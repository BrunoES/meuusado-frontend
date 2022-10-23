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
import com.example.meuapp.utils.Constants
import com.example.meuapp.utils.Retrofit2Api
import com.example.meuapp.utils.TokenUtils
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class DetalheAnuncio : AppCompatActivity() {

    lateinit var valor: TextView
    lateinit var titulo: TextView
    lateinit var descricao: TextView
    lateinit var txt_ano_detalhe_anuncio: TextView
    lateinit var carouselView : CarouselView
    var imageArray:ArrayList<Drawable> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_anuncio)

        var byteImage : ByteArray? = Base64.getDecoder().decode(Constants.BLANK_IMAGE)
        var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size) })
        imageArray.add(image)
        imageArray.add(image)
        imageArray.add(image)
        imageArray.add(image)
        imageArray.add(image)
        imageArray.add(image)

        val anuncioId = intent.getStringExtra("anuncio_id")
        buscaDetalhesAnuncio(Integer.parseInt(anuncioId))

        valor = findViewById(R.id.txt_valor_detalhe_anuncio)
        titulo = findViewById(R.id.txt_titulo_detalhe_anuncio)
        descricao = findViewById(R.id.txt_descricao_detalhe_anuncio)
        txt_ano_detalhe_anuncio = findViewById(R.id.txt_ano_detalhe_anuncio)

        carouselView = findViewById(R.id.carouselView)
        carouselView.pageCount = 6
        carouselView.setImageListener(imageListener)
        carouselView.stopCarousel()
    }


    var imageListener = ImageListener {
        position, imageView -> imageView.setImageDrawable(imageArray[position])
    }

    private fun buscaDetalhesAnuncio(anuncioId: Int) {
        val token : String = TokenUtils.getToken(this)

        Toast.makeText(applicationContext, "Chamou API de busca de detalhes de Anúncio 1", Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()

        Toast.makeText(applicationContext, "Chamou API de busca de detalhes de Anúncio ", Toast.LENGTH_LONG).show()

        val retrofitData = retrofitBuilder.buscaDetalhesAnuncio(token, anuncioId)

        println("LogInfoInicioDetalhe")
        println(retrofitData.request().url())
        println(retrofitData.request().method())
        println(retrofitData.request().headers())
        println(retrofitData.request().body())
        println("LogInfoFimDetalhe")

        /*
        var res : Response<AnuncioResponseDTO> = retrofitData.execute()
        if(res != null) {
            var response = res.body()
            var base64Imagem = ""
            if (response != null) {
                if (response.base64Imagem.isNullOrEmpty()) {
                    base64Imagem = Constants.BLANK_IMAGE
                } else {
                    base64Imagem = response.base64Imagem
                }
            }

            if (response != null) {
                valor.setText("R$ " + NumberFormat.getNumberInstance(Locale.US).format(response.valor).toString().replace(",", "."))
                titulo.setText(response.titulo)
                descricao.setText(response.descricao)
                txt_ano_detalhe_anuncio.setText(response.ano.toString())

                var byteImage : ByteArray? = Base64.getDecoder().decode(base64Imagem)
                var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size) })

                image_view.setImageDrawable(image)
                imageArray.add(0, image)
            }
        }*/

        retrofitData.enqueue(
            object : Callback<AnuncioResponseDTO> {
                override fun onFailure(call: Call<AnuncioResponseDTO>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de detalhes de Anúncio", Toast.LENGTH_LONG).show()
                }
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<AnuncioResponseDTO>, response: Response<AnuncioResponseDTO>) {
                    var response = response.body()
                    var base64Imagem = ""
                    if (response != null) {
                        if (response.base64Imagem.isNullOrEmpty()) {
                            base64Imagem = Constants.BLANK_IMAGE
                        } else {
                            base64Imagem = response.base64Imagem
                        }
                    }

                    if (response != null) {
                        var listAnuncioFotosBase64 : List<String> = response.listAnuncioFotosBase64
                        valor.setText("R$ " + NumberFormat.getNumberInstance(Locale.US).format(response.valor).toString().replace(",", "."))
                        titulo.setText(response.titulo)
                        descricao.setText(response.descricao)
                        txt_ano_detalhe_anuncio.setText(response.ano.toString())

                        /*
                        var byteImage : ByteArray? = Base64.getDecoder().decode(base64Imagem)
                        var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size) })
                        image_view.setImageDrawable(image)
                        */

                        var x : String = ""
                        var byteImage: ByteArray? = Base64.getDecoder().decode(x)
                        var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size ) })
                        val add = imageArray.add(image)
                        carouselView.pageCount = carouselView.pageCount + 1

                        imageArray.clear()

                        for(foto in listAnuncioFotosBase64) {
                            var byteImage: ByteArray? = Base64.getDecoder().decode(foto)
                            var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size ) })
                            imageArray.add(image)
                        }
                        carouselView.pageCount = listAnuncioFotosBase64.size
                        carouselView.playCarousel()
                    }
                }
            }
        )
    }

}