package com.example.foodfusion.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.util.ConnectionManager
import com.example.foodfusion.R
import org.json.JSONObject

class LoginPage : AppCompatActivity() {

    lateinit var txtNumber:EditText
    lateinit var txtPassword:EditText
    lateinit var btnLogin:Button
    lateinit var txtForgetPassword:TextView
    lateinit var txtSignUp:TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        sharedPreferences = getSharedPreferences(getString(R.string.food_fusion_prefs),
            Context.MODE_PRIVATE)
        txtNumber = findViewById(R.id.editNumberLogin)
        txtPassword = findViewById(R.id.editPasswordLogin)
        btnLogin = findViewById(R.id.buttonLogin)
        txtForgetPassword = findViewById(R.id.txtForgetPassword)
        txtSignUp = findViewById(R.id.signUpLogin)

        txtForgetPassword.setOnClickListener{
            val intent = Intent(this@LoginPage, ForgetPasswordPage::class.java)
            startActivity(intent)
        }

        txtSignUp.setOnClickListener{
            val intent = Intent(this@LoginPage, RegistrationPage::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            if(txtNumber.text.length != 10 || txtPassword.text.length < 4 )
            {
                Toast.makeText(this@LoginPage,"Enter Valid Credentails.",Toast.LENGTH_SHORT).show()
            }
            else{
//                Toast.makeText(this@LoginPage,"You click the LOGIN button",Toast.LENGTH_SHORT).show()
                val url = "http://13.235.250.119/v2/login/fetch_result/"
                val queue = Volley.newRequestQueue(this@LoginPage)

                if(ConnectionManager().checkConnectivity(this@LoginPage))
                {
                    val jsonParams = JSONObject()
                    jsonParams.put("mobile_number",txtNumber.text)
                    jsonParams.put("password",txtPassword.text)

                    val jsonRequest =object: JsonObjectRequest(Method.POST,url,jsonParams,
                        Response.Listener {
                            val data = it.getJSONObject("data")
                            if(data.getBoolean("success"))
                            {
                                val allData = data.getJSONObject("data")
                                Toast.makeText(this@LoginPage,"You logged in successfully",Toast.LENGTH_SHORT).show()
                                sharedPreferences.edit().putString("user_id",allData.getString("user_id")).apply()
                                sharedPreferences.edit().putString("name",allData.getString("name")).apply()
                                sharedPreferences.edit().putString("email",allData.getString("email")).apply()
                                sharedPreferences.edit().putString("mobile_number",allData.getString("mobile_number")).apply()
                                sharedPreferences.edit().putString("address",allData.getString("address")).apply()
                                sharedPreferences.edit().putBoolean("isLogged",true).apply()
//                                Toast.makeText(this@LoginPage,"No issue in Shared Preferences",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginPage, HomePage::class.java)
                                startActivity(intent)
                            }
                            else{
                                Toast.makeText(this@LoginPage, data.getString("errorMessage"),Toast.LENGTH_SHORT).show()
                            }
                    },Response.ErrorListener {
                        Toast.makeText(this@LoginPage,"Some Error Occurred -> ErrorListener",Toast.LENGTH_SHORT).show()
                    }){
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers =HashMap<String,String>()
                            headers["Content-type"]="application/json"
                            headers["token"]="fc39dc6d113be8"
                            return headers
                        }
                    }
                    queue.add(jsonRequest)
                }
                else{
                    val dailog = AlertDialog.Builder(this@LoginPage)
                    dailog.setTitle("Error")
                    dailog.setMessage("Internet Connection not Found")
                    dailog.setPositiveButton("Open Settings"){
                            text,listener ->
                        val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                        startActivity(settingIntent)
                        this@LoginPage.finish()
                    }
                    dailog.setNegativeButton("Exit"){
                            text,listener ->
                        ActivityCompat.finishAffinity(this@LoginPage)

                    }
                    dailog.create()
                    dailog.show()
                }
            }

        }

    }

    override fun onPause() {
        super.onPause()
        sharedPreferences = getSharedPreferences(getString(R.string.food_fusion_prefs),
            Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean("isLogged",false))
            finish()
    }
}