package com.example.foodfusion.activity

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.R
import com.example.foodfusion.util.ConnectionManager
import org.json.JSONObject

class ForgetPasswordPage : AppCompatActivity() {

    lateinit var txtNumberForget:EditText
    lateinit var txtEmailForget:EditText
    lateinit var btnNextForget: Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_page)

        txtNumberForget = findViewById(R.id.editNumberForget)
        txtEmailForget = findViewById(R.id.editEmailForget)
        btnNextForget = findViewById(R.id.buttonNext)

        btnNextForget.setOnClickListener{
            if(ConnectionManager().checkConnectivity(this@ForgetPasswordPage))
            {
                val url = "http://13.235.250.119/v2/forgot_password/fetch_result"

                val jsonParams = JSONObject()
                jsonParams.put("mobile_number",txtNumberForget.text)
                jsonParams.put("email",txtEmailForget.text)

                val queue = Volley.newRequestQueue(this@ForgetPasswordPage)

                val jsonRequest = object: JsonObjectRequest(Method.POST,url,jsonParams,Response.Listener {
                    val data = it.getJSONObject("data")
                    if(data.getBoolean("success"))
                    {
                        Toast.makeText(this@ForgetPasswordPage, "OTP send on the registered Email Address.",Toast.LENGTH_SHORT).show()
                        sharedPreferences = this@ForgetPasswordPage.getSharedPreferences(R.string.food_fusion_prefs.toString(),
                            MODE_PRIVATE)
                        sharedPreferences.edit().putString("mobile_number",txtNumberForget.text.toString()).apply()
                        val intent = Intent(this@ForgetPasswordPage,ResetPassword::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this@ForgetPasswordPage, data.getString("errorMessage"),Toast.LENGTH_SHORT).show()
                    }
                },Response.ErrorListener {
                    Toast.makeText(this@ForgetPasswordPage,"Some Error Occurred -> ErrorListener",
                        Toast.LENGTH_SHORT).show()
                }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "fc39dc6d113be8"
                        return headers
                    }
                }

                queue.add(jsonRequest)
            }
            else
            {
                val dailog = AlertDialog.Builder(this@ForgetPasswordPage)
                dailog.setTitle("Error")
                dailog.setMessage("Internet Connection not Found")
                dailog.setPositiveButton("Open Settings"){
                        text,listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    this@ForgetPasswordPage.finish()
                }
                dailog.setNegativeButton("Exit"){
                        text,listener ->
                    ActivityCompat.finishAffinity(this@ForgetPasswordPage)

                }
                dailog.create()
                dailog.show()
            }
        }

    }
}