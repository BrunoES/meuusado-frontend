package com.example.meuapp.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meuapp.R
import com.example.meuapp.dtos.response.AnuncioResumidoResponseDTO
import com.example.meuapp.recicleviews.items.AnuncioItem
import com.example.meuapp.recicleviews.items.CustomAdapter
import com.example.meuapp.utils.LocationUtils
import com.example.meuapp.utils.Retrofit2Api
import com.example.meuapp.utils.TokenUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnunciosActive : AppCompatActivity(), CustomAdapter.OnItemClickListener {
    private val USER_ID = "user_id"

    private var itemsList = ArrayList<AnuncioItem>()

    lateinit var btn_cadastrarAnuncio: Button
    lateinit var customAdapter: CustomAdapter
    lateinit var txt_pesquisa_anuncios: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncios_active)

        txt_pesquisa_anuncios = findViewById(R.id.txt_pesquisa_anuncios)

        title = "Meu Usado"

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        customAdapter = CustomAdapter(itemsList, this)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter

        btn_cadastrarAnuncio = findViewById(R.id.btn_cadastrar_anuncio)

        btn_cadastrarAnuncio.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@AnunciosActive, CadastroAnuncioActive::class.java).apply {
                    putExtra(USER_ID, "1")
                }
                startActivity(intent)
            }
        })

        txt_pesquisa_anuncios.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                Toast.makeText(applicationContext, "Change Pesquisando Anúncios com Filtro: " + query, Toast.LENGTH_LONG).show()
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, "Pesquisando Anúncios com Filtro: " + query, Toast.LENGTH_LONG).show()
                buscaAnuncios(query)
                return true
            }
        })

        initBuscas()
    }

    /*
    fun createLocationRequest() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    */


    override fun onItemClick(position: Int) {
        //customAdapter.notifyItemChanged(position) ? Precisa ?;
        val intent = Intent(this@AnunciosActive, DetalheAnuncio::class.java).apply {
            putExtra("anuncio_id", itemsList.get(position).idAnuncio.toString())
        }
        startActivity(intent)
    }

    private fun initBuscas() {
        // buscaAnuncios()
        buscaAnunciosPorLocation()
        println("Chamou notifyDataSetChanged")
        println("Size itemsList")
        println(itemsList.size)
    }

    private fun buscaAnuncios(): List<AnuncioResumidoResponseDTO>? {
        var list : List<AnuncioResumidoResponseDTO>? = null
        val token : String = TokenUtils.getToken(this)

        Toast.makeText(applicationContext, "Chamou API de Busca de Anúncios 1", Toast.LENGTH_LONG).show()

        Toast.makeText(applicationContext, "Token: " + token, Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()
        val retrofitData = retrofitBuilder.buscarAnuncios(token)

        itemsList.clear()
        retrofitData.enqueue(
            object : Callback<List<AnuncioResumidoResponseDTO>> {
                override fun onFailure(call: Call<List<AnuncioResumidoResponseDTO>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de Busca de Anúncios", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<AnuncioResumidoResponseDTO>>, response: Response<List<AnuncioResumidoResponseDTO>>) {
                    Toast.makeText(applicationContext, "Anúncios buscados com sucesso.", Toast.LENGTH_LONG).show()
                    println("Response--")
                    response.body()?.forEach({
                        x -> itemsList.add(AnuncioItem(x.idAnuncio, x.titulo, x.base64ImgPrincMin, x.valor))
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

    private fun buscaAnunciosPorLocation(): List<AnuncioResumidoResponseDTO>? {
        var list : List<AnuncioResumidoResponseDTO>? = null
        val token : String = TokenUtils.getToken(this)
        var coordinates = LocationUtils.getCoordinates(this)

        Toast.makeText(applicationContext, "Chamou API de Busca de Anúncios 1", Toast.LENGTH_LONG).show()

        Toast.makeText(applicationContext, "Token: " + token, Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()
        val retrofitData = retrofitBuilder.buscarAnunciosPorLocation(token, coordinates)

        itemsList.clear()
        retrofitData.enqueue(
            object : Callback<List<AnuncioResumidoResponseDTO>> {
                override fun onFailure(call: Call<List<AnuncioResumidoResponseDTO>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de Busca de Anúncios", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<AnuncioResumidoResponseDTO>>, response: Response<List<AnuncioResumidoResponseDTO>>) {
                    Toast.makeText(applicationContext, "Anúncios buscados com sucesso.", Toast.LENGTH_LONG).show()
                    println("Response--")
                    response.body()?.forEach({
                            x -> itemsList.add(AnuncioItem(x.idAnuncio, x.titulo, x.base64ImgPrincMin, x.valor))
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

    private fun buscaAnuncios(query : String): List<AnuncioResumidoResponseDTO>? {
        var list : List<AnuncioResumidoResponseDTO>? = null

        val token : String = TokenUtils.getToken(this)

        Toast.makeText(applicationContext, "Chamou API de Busca de Anúncios considerando query: $query", Toast.LENGTH_LONG).show()

        Toast.makeText(applicationContext, "Token: " + token, Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()
        val retrofitData = retrofitBuilder.buscarAnuncios(token, query)
        //retrofitData.request().header("Authorization", token)

        itemsList.clear()
        retrofitData.enqueue(
            object : Callback<List<AnuncioResumidoResponseDTO>> {
                override fun onFailure(call: Call<List<AnuncioResumidoResponseDTO>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de Busca de Anúncios", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<AnuncioResumidoResponseDTO>>, response: Response<List<AnuncioResumidoResponseDTO>>) {
                    Toast.makeText(applicationContext, "Anúncios buscados com sucesso.", Toast.LENGTH_LONG).show()
                    println("Response--")
                    response.body()?.forEach({
                            x -> itemsList.add(AnuncioItem(x.idAnuncio, x.titulo, x.base64ImgPrincMin, x.valor))
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