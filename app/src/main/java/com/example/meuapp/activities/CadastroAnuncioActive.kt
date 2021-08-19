package com.example.meuapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.meuapp.MainActivity
import com.example.meuapp.R

import com.example.meuapp.dtos.response.AnuncioResponseDTO
import com.example.meuapp.dtos.request.CadastroAnuncioDTO
import com.example.meuapp.utils.Retrofit2Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*


// https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
class CadastroAnuncioActive : AppCompatActivity() {

    val PICK_IMAGE = 1

    lateinit var titulo: EditText
    lateinit var descricao: EditText
    lateinit var valor: EditText
    lateinit var btn_add_imagem_cadastro_anuncio: Button
    lateinit var btn_salvar_cadastro_anuncio: Button
    lateinit var image_view: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_anuncio_active)

        titulo = findViewById(R.id.txt_titulo_cadastro_anuncio)
        descricao = findViewById(R.id.txt_descricao_cadastro_anuncio)
        valor = findViewById(R.id.txt_valor_cadastro_anuncio)

        btn_add_imagem_cadastro_anuncio = findViewById(R.id.btn_add_imagem_cadastro_anuncio)
        btn_salvar_cadastro_anuncio = findViewById(R.id.btn_salvar_cadastro_anuncio)
        image_view = findViewById(R.id.imageView)

        btn_add_imagem_cadastro_anuncio.setOnClickListener {
            pickImageFromGallery();
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
            */
        }

        btn_salvar_cadastro_anuncio.setOnClickListener {
            cadastroAnuncio()
        }

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        pickImageFromGallery();
        /*
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        */
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_view.setImageURI(data?.data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun cadastroAnuncio() {

        Toast.makeText(applicationContext, "Chamou API de Cadastro 1", Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()

        val valorConversao : String
        valorConversao = valor.text.toString()



        val bitmapDrawable : BitmapDrawable = image_view.getDrawable() as BitmapDrawable
        val bitmap : Bitmap = bitmapDrawable.getBitmap()
        val stream : ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);


        /*var byte[] imageInByte = stream.toByteArray()
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
        stream.toByteArray()
        */

        val encodedfile : String = Base64.getEncoder().encodeToString(stream.toByteArray())

        println("Base64")
        println(encodedfile)
        println("Base64")

        val cadastroAnuncioDTO = CadastroAnuncioDTO(1L, 1L, titulo.text.toString(), descricao.text.toString(),  valorConversao.toFloat(), encodedfile)
        val retrofitData = retrofitBuilder.cadastrarAnuncio(cadastroAnuncioDTO)

        println("LogInfoInicioCad")
        println(retrofitData.request().url())
        println(retrofitData.request().method())
        println(retrofitData.request().headers())
        println(retrofitData.request().body())
        println(encodedfile)
        println("LogInfoFimCad")

        retrofitData.enqueue(
            object : Callback<AnuncioResponseDTO> {
                override fun onFailure(call: Call<AnuncioResponseDTO>, t: Throwable) {
                    Toast.makeText(applicationContext, "Falha ao chamar API de Cadastro", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<AnuncioResponseDTO>, response: Response<AnuncioResponseDTO>) {
                    var response = response.body()
                    var idAnuncio : Long = 0L

                    println("RespostaInicio:")
                    if (response != null) {
                        idAnuncio = response.idAnuncio
                        println(response.idAnuncio)
                    }
                    println("RespostaFim:")
                    if(idAnuncio != null) {
                        Toast.makeText(applicationContext, "Usuário criado com sucesso.", Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, "Realize o login.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@CadastroAnuncioActive, MainActivity::class.java).apply {
                        }
                        startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, "Erro ao criar Anúncio.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

}

/*
        btn_add_imagem_cadastro_anuncio.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)

                var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        // There are no request codes
                        val data: Intent? = result.data
                        //doSomeOperations()
                    }
                }

                fun openSomeActivityForResult() {
                    val intent = Intent(this@CadastroAnuncioActive, CadastroAnuncioActive::class.java)
                    resultLauncher.launch(intent)
                }

                val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length)
                imageView.setImageBitmap(decodedImage)
            }
        })
        */