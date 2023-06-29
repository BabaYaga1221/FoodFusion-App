package com.example.foodfusion.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.R
import org.json.JSONObject

class ResetPassword : AppCompatActivity() {

    lateinit var txtOTPReset:EditText
    lateinit var txtNewPasswordReset:EditText
    lateinit var txtPasswordConfirmReset:EditText
    lateinit var btnSubmitReset:Button
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        txtOTPReset = findViewById(R.id.editOTPReset)
        txtNewPasswordReset = findViewById(R.id.editNewPasswordReset)
        txtPasswordConfirmReset = findViewById(R.id.editConfirmPasswordReset)
        btnSubmitReset = findViewById(R.id.btnSubmitReset)

        btnSubmitReset.setOnClickListener{
            if(txtNewPasswordReset.text == txtPasswordConfirmReset.text) {
                sharedPreferences = this@ResetPassword.getSharedPreferences(
                    R.string.food_fusion_prefs.toString(),
                    MODE_PRIVATE
                )
                val url = "http://13.235.250.119/v2/reset_password/fetch_result"
                val jsonParams = JSONObject()
                jsonParams.put(
                    "mobile_number",
                    sharedPreferences.getString("mobile_number", "null")
                )
                jsonParams.put("password", txtNewPasswordReset.text)
                jsonParams.put("otp", txtOTPReset.text)

                val queue = Volley.newRequestQueue(this@ResetPassword)
                val jsonRequest =
                    object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {
                        val data = it.getJSONObject("data")
                        if (data.getBoolean("success")) {
                            Toast.makeText(
                                this@ResetPassword, data.getString("successMessage"),
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@ResetPassword, LoginPage::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(
                                this@ResetPassword, data.getString("errorMessage"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }, Response.ErrorListener {
                        Toast.makeText(
                            this@ResetPassword, "Some Error Occurred -> ErrorListener",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
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
                Toast.makeText(this@ResetPassword,"Enter Same Password",Toast.LENGTH_SHORT).show()
            }
        }

    }
}