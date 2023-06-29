package com.example.foodfusion.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.foodfusion.R

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            sharedPreferences = getSharedPreferences(getString(R.string.food_fusion_prefs),
                Context.MODE_PRIVATE)
            if(sharedPreferences.getBoolean("isLogged",false))
            {
                val intent = Intent(this@MainActivity, HomePage::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this@MainActivity, LoginPage::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}