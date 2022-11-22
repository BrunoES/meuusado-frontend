package com.example.meuapp

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
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.meuapp.activities.AnunciosActive
import com.example.meuapp.activities.CadastroUsuarioActive
import com.example.meuapp.dtos.request.CadastroUsuarioDTO
import com.example.meuapp.dtos.request.LoginDTO
import com.example.meuapp.dtos.response.UsuarioResponseDTO
import com.example.meuapp.utils.Retrofit2Api
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    private val USER_ID = "user_id"

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var botao1: Button
    lateinit var botao2: Button
    lateinit var botao_login_facebook: Button

    private var locationManager : LocationManager? = null
    private val EMAIL = "email"

    var login = true
    lateinit var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create();
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        setContentView(R.layout.activity_main)
        botao1 = findViewById(R.id.btn_nome)
        botao2 = findViewById(R.id.btn_cadastre_se)
        botao_login_facebook = findViewById(R.id.btn_login_facebook)

        email = findViewById(R.id.txt_email)
        password = findViewById(R.id.txt_password)

        botao1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                login(email.text.toString(), password.text.toString())
            }
        })

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        if(isLoggedIn) {
            val graphRequest = GraphRequest.newMeRequest(accessToken) { `object`, response ->
                var email = ""

                try {
                    //here is the data that you want
                    Log.d("FBLOGIN_JSON_RES", `object`.toString())
                    if (`object` != null) {
                        if (`object`.has("email")) {
                            email = `object`.getString("email")
                        }

                        if(!email.isEmpty() ) {
                            login(email, "")
                        } else {
                            Log.e("FBLOGIN_FAILD", `object`.toString())
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            login(email.text.toString(), password.text.toString())
        }

        botao_login_facebook.setOnClickListener(View.OnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    //loginResult.recentlyGrantedPermissions.toString()
                    //var profile : Profile? = Profile.getCurrentProfile()

                    var profile : Profile? = ProfileManager.getInstance().currentProfile

                    if (profile != null) {
                        Log.d("MainActivity", "Facebook token: " + profile.toJSONObject())
                    }

                    Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)

                    val graphRequest = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                        var id = ""
                        var nome = ""
                        var email = ""

                        try {
                            //here is the data that you want
                            Log.d("FBLOGIN_JSON_RES", `object`.toString())
                            if (`object` != null) {
                                if (`object`.has("id")) {
                                    id = `object`.getString("id")
                                }
                                if (`object`.has("name")) {
                                    nome = `object`.getString("name")
                                }
                                if (`object`.has("email")) {
                                    email = `object`.getString("email")
                                }

                                if(!id.isEmpty() && !nome.isEmpty() && !email.isEmpty() ) {
                                    val retrofitBuilder = Retrofit2Api.getBuilder()

                                    val cadastroUsuarioDTO = CadastroUsuarioDTO(nome, email, "")
                                    val retrofitData = retrofitBuilder.cadastrarUsuario(cadastroUsuarioDTO)

                                    retrofitData.enqueue(
                                        object : Callback<UsuarioResponseDTO> {
                                            override fun onFailure(call: Call<UsuarioResponseDTO>, t: Throwable) {
                                                Toast.makeText(applicationContext, "Falha ao chamar API de Cadastro", Toast.LENGTH_LONG).show()
                                            }
                                            override fun onResponse(call: Call<UsuarioResponseDTO>, response: Response<UsuarioResponseDTO>) {
                                                var response = response.body()
                                                var idUsuario : Long = 0L
                                                var email : String = ""
                                                var password : String = ""

                                                println("RespostaInicio:")
                                                if (response != null) {
                                                    idUsuario = response.idUsuario
                                                    email = response.email
                                                    password = response.password
                                                    println(response.idUsuario)
                                                }
                                                println("RespostaFim:")
                                                if(!idUsuario.equals(0L)) {
                                                    Toast.makeText(applicationContext, "Usuário criado com sucesso.", Toast.LENGTH_LONG).show()
                                                    Toast.makeText(applicationContext, "Realizando login.", Toast.LENGTH_LONG).show()
                                                    login(email, password)
                                                } else {
                                                    Toast.makeText(applicationContext, "Erro ao criar usuário.", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }
                                    )
                                } else {
                                    Log.e("FBLOGIN_FAILD", `object`.toString())
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    val parameters = Bundle()
                    parameters.putString("fields", "name,email,id")
                    graphRequest.parameters = parameters
                    graphRequest.executeAsync()

                    // startActivity(Intent(applicationContext, AnunciosActive::class.java))
                }

                override fun onCancel() {
                    Log.d("MainActivity", "Facebook onCancel.")
                }

                override fun onError(error: FacebookException) {
                    Log.d("MainActivity", "Facebook onError.")
                }
            })
        })

        botao2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, CadastroUsuarioActive::class.java).apply {
                    putExtra(USER_ID, "1")
                }
                startActivity(intent)
            }
        })

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    println(loginResult.accessToken)
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Toast.makeText(applicationContext, "" + location.longitude + ":" + location.latitude, Toast.LENGTH_LONG).show()
            val sharedPreferences : SharedPreferences = getSharedPreferences("meuUsadoPref", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            //editor.putString("latitude", location.latitude.toString())
            //editor.putString("longitude", location.longitude.toString())
            // Mock:
            editor.putString("latitude", "-23.3383")
            editor.putString("longitude", "-52.0887")
            editor.commit()

            if(login) callAnunciosActivity()
            login = false
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
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 1000.0f, locationListener)
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 1000.0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available: " + ex.stackTrace)
            Toast.makeText(applicationContext, "" + ex.stackTrace, Toast.LENGTH_LONG).show()
        }
    }

    private fun initGeolocation() {
        var geoLocateDisabled : Boolean = !locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!! ||
                !locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!!

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
        builder.setTitle("Hummm, sua localização está desligada")
        builder.setMessage("Ative sua localização para ver os anúncios perto de você")
        builder.setPositiveButton("Ligar",
            DialogInterface.OnClickListener { dialogInterface, i -> // Show location settings when the user acknowledges the alert dialog
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            })
        val alertDialog: Dialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    private fun login(email : String, password : String) {

        Toast.makeText(applicationContext, "Chamou API de Login 2", Toast.LENGTH_LONG).show()

        val retrofitBuilder = Retrofit2Api.getBuilder()

        Toast.makeText(applicationContext, "Chamou API de Login 2", Toast.LENGTH_LONG).show()

        val loginDto = LoginDTO(email, password)
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

}