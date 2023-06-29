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
import com.example.foodfusion.fragments.FavoriteFragment
import com.example.foodfusion.fragments.FoodListFragment
import com.example.foodfusion.fragments.HomePageFragment

class FoodList : AppCompatActivity() {

    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        coordinatorLayout = findViewById(R.id.coordinatorLayoutFoodList)
        toolbar = findViewById(R.id.ToolbarFoodList)
        frameLayout = findViewById(R.id.frameFoodList)


        sharedPreferences = getSharedPreferences(getString(R.string.food_fusion_prefs),Context.MODE_PRIVATE)

        setUpToolBar()

//        Toast.makeText(this@FoodList,"I am in FoodList Activity",Toast.LENGTH_SHORT).show()

        supportFragmentManager.beginTransaction().replace(R.id.frameFoodList,FoodListFragment()).commit()
        supportActionBar?.title = sharedPreferences.getString("foodName","NO NAME")
    }

    fun setUpToolBar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}