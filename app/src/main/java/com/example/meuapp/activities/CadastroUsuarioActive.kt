package com.example.meuapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meuapp.MainActivity
import com.example.meuapp.R
import com.example.meuapp.dtos.request.CadastroUsuarioDTO
import com.example.meuapp.dtos.response.UsuarioResponseDTO
import com.example.meuapp.utils.Retrofit2Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroUsuarioActive : AppCompatActivity() {

    private val USER_ID = "user_id"

    lateinit var nome: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var password_conf: EditText
    lateinit var btn_cadastro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        nome = findViewById(R.id.txt_nome_cadastro_usuario)
        email = findViewById(R.id.txt_email_cadastro_usuario)
        password = findViewById(R.id.txt_password_cadastro_usuario)
        password_conf = findViewById(R.id.txt_password_confirmacao_cadastro_usuario)

        btn_cadastro = findViewById(R.id.btn_cadastrar_cadastro_usuario)

        btn_cadastro.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cadastroUsuario()
            }
        })
    }

    private fun cadastroUsuario() {

        Toast.makeText(applicationContext, "Chamou API de Cadastro 1", Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()

        Toast.makeText(applicationContext, "Chamou API de Cadastro 2", Toast.LENGTH_LONG).show()

        val cadastroUsuarioDTO = CadastroUsuarioDTO(nome.text.toString(), email.text.toString(), password.text.toString())
        val retrofitData = retrofitBuilder.cadastrarUsuario(cadastroUsuarioDTO)

        println("LogInfoInicioCad")
        println(retrofitData.request().url())
        println(retrofitData.request().method())
        println(retrofitData.request().headers())
        println(retrofitData.request().body())
        println("LogInfoFimCad")

        retrofitData.enqueue(
            object : Callback<UsuarioResponseDTO> {
                override fun onFailure(call: Call<UsuarioResponseDTO>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de Login", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<UsuarioResponseDTO>, response: Response<UsuarioResponseDTO>) {
                    var response = response.body()
                    var idUsuario : Long = 0L

                    println("RespostaInicio:")
                    if (response != null) {
                        idUsuario = response.idUsuario
                        println(response.idUsuario)
                    }
                    println("RespostaFim:")
                    if(idUsuario != null) {
                        Toast.makeText(applicationContext, "Usuário criado com sucesso.", Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, "Realize o login.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@CadastroUsuarioActive, MainActivity::class.java).apply {
                            putExtra(USER_ID, idUsuario)
                        }
                        startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, "Erro ao criar usuário.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

}