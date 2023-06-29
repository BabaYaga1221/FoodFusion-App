package com.example.foodfusion.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.foodfusion.R
import com.example.foodfusion.fragments.CartPageFragment
import com.example.foodfusion.fragments.FoodListFragment

class CartPage : AppCompatActivity() {

    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_page)

        coordinatorLayout = findViewById(R.id.coordinatorLayoutCartList)
        toolbar = findViewById(R.id.ToolbarCartList)
        frameLayout = findViewById(R.id.frameCartList)


//        sharedPreferences = getSharedPreferences(getString(R.string.food_fusion_prefs), Context.MODE_PRIVATE)

        setUpToolBar()

        val foodIdList:ArrayList<HashMap<String,String>> = intent.getSerializableExtra("foodIdList") as ArrayList<HashMap<String,String>>
        val totalMoney:Int = intent.getIntExtra("totalMoney",0)

        supportFragmentManager.beginTransaction().replace(R.id.frameCartList, CartPageFragment(foodIdList,totalMoney)).commit()
        supportActionBar?.title = "My Cart"
//        Toast.makeText(this@CartPage,"This is CartPage Activity",Toast.LENGTH_SHORT).show()
    }

    fun setUpToolBar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}