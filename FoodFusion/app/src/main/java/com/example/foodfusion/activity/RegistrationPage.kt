package com.example.foodfusion.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.R
import org.json.JSONObject

class RegistrationPage : AppCompatActivity() {

    lateinit var txtNameRegister:EditText
    lateinit var txtEmailRegister:EditText
    lateinit var txtNumberRegister:EditText
    lateinit var txtLocationRegister: EditText
    lateinit var txtPasswordRegister: EditText
    lateinit var txtPasswordConfirmRegister: EditText
    lateinit var btnRegister:Button
    lateinit var imgBackButton: ImageView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)

        txtNameRegister = findViewById(R.id.txtNameRegister)
        txtEmailRegister = findViewById(R.id.txtEmailRegister)
        txtNumberRegister = findViewById(R.id.txtMobileRegister)
        txtLocationRegister = findViewById(R.id.txtDeliveryRegister)
        txtPasswordRegister = findViewById(R.id.txtPasswordRegister)
        txtPasswordConfirmRegister = findViewById(R.id.txtPasswordConfirmRegister)
        btnRegister = findViewById(R.id.btnRegister)
        imgBackButton = findViewById(R.id.imgBackButton)

        btnRegister.setOnClickListener{
            if(txtNameRegister.text.length >= 3 && txtNumberRegister.text.length == 10 && txtPasswordRegister.text.length >=4)
            {
                val url = "http://13.235.250.119/v2/register/fetch_result"
                val queue = Volley.newRequestQueue(this@RegistrationPage)

                val jsonParams = JSONObject()
                jsonParams.put("name",txtNameRegister.text)
                jsonParams.put("mobile_number",txtNumberRegister.text)
                jsonParams.put("password",txtPasswordRegister.text)
                jsonParams.put("address",txtLocationRegister.text)
                jsonParams.put("email",txtEmailRegister.text)

                val jsonRequest = object:JsonObjectRequest(Method.POST,url,jsonParams, Response.Listener {
                    val data = it.getJSONObject("data")
                    if(data.getBoolean("success"))
                    {
                        val allData = data.getJSONObject("data")
                        Toast.makeText(this@RegistrationPage,"You are Successfully Registered!!",Toast.LENGTH_SHORT).show()
                        sharedPreferences = this@RegistrationPage.getSharedPreferences(R.string.food_fusion_prefs.toString(),
                            MODE_PRIVATE)
                        sharedPreferences.edit().putString("user_id",allData.getString("user_id")).apply()
                        sharedPreferences.edit().putString("name",allData.getString("name")).apply()
                        sharedPreferences.edit().putString("email",allData.getString("email")).apply()
                        sharedPreferences.edit().putString("mobile_number",allData.getString("mobile_number")).apply()
                        sharedPreferences.edit().putString("address",allData.getString("address")).apply()
                        val intent = Intent(this@RegistrationPage, HomePage::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@RegistrationPage,data.getString("errorMessage"),Toast.LENGTH_SHORT).show()
                    }

                },Response.ErrorListener {
                    Toast.makeText(this@RegistrationPage,"Some Error Occurred -> ErrorListener",Toast.LENGTH_SHORT).show()
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
            else
            {
                Toast.makeText(this@RegistrationPage,"Enter Valid Credentials!!",Toast.LENGTH_SHORT).show()
            }
        }

    }
}