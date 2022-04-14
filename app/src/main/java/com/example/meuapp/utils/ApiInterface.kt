package com.example.meuapp.utils

import com.example.meuapp.items.MyDataItem
import com.example.meuapp.dtos.request.CadastroAnuncioDTO
import com.example.meuapp.dtos.request.CadastroUsuarioDTO
import com.example.meuapp.dtos.request.LoginDTO
import com.example.meuapp.dtos.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("posts")
    fun getData(): Call<List<MyDataItem>>

    @GET("api/v1/anuncio")
    fun buscarAnuncios() : Call<List<AnuncioResumidoResponseDTO>>

    @GET("api/v1/anuncio/filter/{query}")
    fun buscarAnuncios(@Path("query") query: String) : Call<List<AnuncioResumidoResponseDTO>>

    @POST("api/v1/login")
    fun login(@Body loginDTO: LoginDTO) : Call<LoginResponseDTO>

    @POST("api/v1/usuario")
    fun cadastrarUsuario(@Body cadastroUsuarioDTO: CadastroUsuarioDTO) : Call<UsuarioResponseDTO>

    @POST("api/v1/anuncio")
    fun cadastrarAnuncio(@Body cadastroAnuncioDTO: CadastroAnuncioDTO) : Call<AnuncioResponseDTO>

    @GET("api/v1/anuncio/{anuncioId}")
    fun buscaDetalhesAnuncio(@Path("anuncioId") anuncioId: Int) : Call<AnuncioResponseDTO>

    @GET("api/v1/marca")
    fun buscarMarcas() : Call<List<MarcaResponseDTO>>

    @GET("api/v1/modelo/marca/{marcaId}")
    fun buscarModelosPorMarca(@Path("marcaId") marcaId : Long) : Call<List<ModeloResponseDTO>>

}