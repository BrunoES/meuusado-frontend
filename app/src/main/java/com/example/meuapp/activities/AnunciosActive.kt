package com.example.meuapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meuapp.recicleviews.items.CustomAdapter
import com.example.meuapp.R
import com.example.meuapp.dtos.response.AnuncioResumidoResponseDTO
import com.example.meuapp.recicleviews.items.AnuncioItem
import com.example.meuapp.utils.Retrofit2Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnunciosActive : AppCompatActivity() {
    private val USER_ID = "user_id"
    private var itemsList = ArrayList<AnuncioItem>()

    lateinit var btn_cadastrarAnuncio: Button
    lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncios_active)

        title = "Meu Usado"

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        customAdapter = CustomAdapter(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
        prepareItems()

        btn_cadastrarAnuncio = findViewById(R.id.btn_cadastrar_anuncio)

        btn_cadastrarAnuncio.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@AnunciosActive, CadastroAnuncioActive::class.java).apply {
                    putExtra(USER_ID, "1")
                }
                startActivity(intent)
            }
        })
    }

    private fun prepareItems() {
        buscaAnuncios()
        println("Chamou notifyDataSetChanged")
        println("Size itemsList")
        println(itemsList.size)
    }

    private fun buscaAnuncios(): List<AnuncioResumidoResponseDTO>? {
        var list : List<AnuncioResumidoResponseDTO>? = null

        Toast.makeText(applicationContext, "Chamou API de Busca de Anúncios 1", Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()
        val retrofitData = retrofitBuilder.buscarAnuncios()

        retrofitData.enqueue(
            object : Callback<List<AnuncioResumidoResponseDTO>> {
                override fun onFailure(call: Call<List<AnuncioResumidoResponseDTO>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de Busca de Anúncios", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<AnuncioResumidoResponseDTO>>, response: Response<List<AnuncioResumidoResponseDTO>>) {
                    Toast.makeText(applicationContext, "Anúncios buscados com sucesso.", Toast.LENGTH_LONG).show()
                    println("Response--")
                    response.body()?.forEach({
                        x -> itemsList.add(AnuncioItem(x.titulo, x.base64ImgPrincMin))
                    })
                    println("Response--")
                    println(itemsList.size)
                    println("Response--")
                    customAdapter.notifyDataSetChanged()
                }
            }
        )
        return list
    }

}