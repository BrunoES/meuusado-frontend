package com.example.meuapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
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
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.meuapp.items.MyDataItem
import com.example.meuapp.activities.AnunciosActive
import com.example.meuapp.activities.CadastroUsuarioActive
import com.example.meuapp.dtos.request.LoginDTO
import com.example.meuapp.utils.ApiInterface
import com.example.meuapp.utils.Retrofit2Api

class MainActivity : AppCompatActivity() {
    private val USER_ID = "user_id"

    lateinit var campo1: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var botao1: Button
    lateinit var botao2: Button

    private var locationManager : LocationManager? = null

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

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Toast.makeText(applicationContext, "" + location.longitude + ":" + location.latitude, Toast.LENGTH_LONG).show()
            val sharedPreferences : SharedPreferences = getSharedPreferences("meuUsadoPref", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("latitude", location.latitude.toString())
            editor.putString("longitude", location.longitude.toString())
            callAnunciosActivity()
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        }
        override fun onProviderEnabled(provider: String) {
        }
        override fun onProviderDisabled(provider: String) {}
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    locationManager?.let { requestLocation(it) }
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    locationManager?.let { requestLocation(it) }
                } else -> {
                Log.d("myTag", "Erro ao acessar localização.")
            }
            }
        }
    }

    private fun requestLocation (locationManager : LocationManager) {
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available: " + ex.stackTrace)
            Toast.makeText(applicationContext, "" + ex.stackTrace, Toast.LENGTH_LONG).show()
        }
    }

    private fun initGeolocation() {
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        var geoLocateDisabled : Boolean = !lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (geoLocateDisabled) {
            requestTurnOnLocation()
        } else {
            Toast.makeText(applicationContext, "Inicializando geolocalização", Toast.LENGTH_LONG).show()
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    private fun requestTurnOnLocation() {
        // Build the alert dialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Location Services Not Active")
        builder.setMessage("Please enable Location Services and GPS")
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialogInterface, i -> // Show location settings when the user acknowledges the alert dialog
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            })
        val alertDialog: Dialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
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
                    val sharedPreferences : SharedPreferences = getSharedPreferences("meuUsadoPref", Context.MODE_PRIVATE)
                    var editor = sharedPreferences.edit()

                    println("RespostaInicio:")
                    if (token != null) {
                        Toast.makeText(applicationContext, "Token JWT: " + token, Toast.LENGTH_LONG).show()
                        editor.putString("token", "Bearer " + token)
                        editor.commit()

                        initGeolocation()
                        println(token)
                    } else {
                        Toast.makeText(applicationContext, "Usuário ou Senha Inválidos.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    private fun callAnunciosActivity() {
        Toast.makeText(applicationContext, "Login realizado com sucesso.", Toast.LENGTH_LONG).show()
        val intent = Intent(this@MainActivity, AnunciosActive::class.java).apply {
            putExtra(USER_ID, "1")
        }
        startActivity(intent)
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