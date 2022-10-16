package com.example.meuapp

import android.accounts.AccountManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent
import android.content.SharedPreferences
import com.example.meuapp.items.MyDataItem
import com.example.meuapp.activities.AnunciosActive
import com.example.meuapp.activities.CadastroUsuarioActive
import com.example.meuapp.dtos.request.LoginDTO
import com.example.meuapp.dtos.response.LoginResponseDTO
import com.example.meuapp.utils.ApiInterface
import com.example.meuapp.utils.Retrofit2Api

class MainActivity : AppCompatActivity() {
    private val USER_ID = "user_id"

    lateinit var campo1: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var botao1: Button
    lateinit var botao2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        campo1 = findViewById(R.id.txt_nome)
        botao1 = findViewById(R.id.btn_nome)
        botao2 = findViewById(R.id.btn_cadastre_se)

        email = findViewById(R.id.txt_email)
        password = findViewById(R.id.txt_password)


        botao1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                login()
            }
        })

        botao2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, CadastroUsuarioActive::class.java).apply {
                    putExtra(USER_ID, "1")
                }
                startActivity(intent)
            }
        })
    }

    private fun login() {

        Toast.makeText(applicationContext, "Chamou API de Login 2", Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()

        Toast.makeText(applicationContext, "Chamou API de Login 2", Toast.LENGTH_LONG).show()

        val loginDto = LoginDTO(email.text.toString(), password.text.toString())
        val retrofitData = retrofitBuilder.login(loginDto)

        println("LogInfoInicio")
        println(retrofitData.request().url())
        println(retrofitData.request().method())
        println(retrofitData.request().headers())
        println(retrofitData.request().body())
        println("LogInfoFim")

        retrofitData.enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de Login", Toast.LENGTH_LONG).show()
                    println("Erro ao realizar login: " + t.message)
                    println(t.stackTrace)
                }
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val token = response.body()
                    val sharedPreferences : SharedPreferences = getSharedPreferences("tokenPref", Context.MODE_PRIVATE)
                    var editor = sharedPreferences.edit()

                    println("RespostaInicio:")
                    if (token != null) {
                        println(token)

                        Toast.makeText(applicationContext, "Login realizado com sucesso.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MainActivity, AnunciosActive::class.java).apply {
                            putExtra(USER_ID, "1")
                        }

                        Toast.makeText(applicationContext, "Token JWT: " + token, Toast.LENGTH_LONG).show()

                        editor.putString("token", "Bearer " + token)
                        editor.commit()

                        // Storage token:
                        // https://stackoverflow.com/questions/34191731/where-to-store-a-jwt-token

                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Usuário ou Senha Inválidos.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(ApiInterface::class.java)


        Toast.makeText(applicationContext, "Chamou API", Toast.LENGTH_LONG).show()

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(call: Call<List<MyDataItem>?>, response: Response<List<MyDataItem>?>) {
                Toast.makeText(applicationContext, "Começou ler retorno da API", Toast.LENGTH_LONG).show()
                val responseBody = response.body()!!
                var myStringBuilder = StringBuilder()

                for (myData in responseBody) {
                    myStringBuilder.append(myData.id)
                    myStringBuilder.append("\n")
                }
                campo1.setText(myStringBuilder)
            }
            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}