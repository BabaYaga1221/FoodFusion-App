package com.example.foodfusion.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.example.foodfusion.R

class OrderPlaced : AppCompatActivity() {

    lateinit var btnConfirm:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)

        btnConfirm = findViewById(R.id.btnConfirm)

        btnConfirm.setOnClickListener{
         val intent = Intent(this@OrderPlaced,HomePage::class.java)
         startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        ActivityCompat.finishAffinity(this@OrderPlaced)
    }
}