package com.example.foodfusion.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foodfusion.*
import com.example.foodfusion.fragments.*
import com.google.android.material.navigation.NavigationView

class HomePage : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationLayout: NavigationView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var txtNameDrawer:TextView
    lateinit var txtNumberDrawer:TextView
    lateinit var headerView:View

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationLayout = findViewById(R.id.navigationLayout)
        headerView = navigationLayout.getHeaderView(0)
        txtNameDrawer = headerView.findViewById(R.id.txtNameDrawer)
        txtNumberDrawer = headerView.findViewById(R.id.txtNumberDrawer)

        sharedPreferences = getSharedPreferences(getString(R.string.food_fusion_prefs),
            Context.MODE_PRIVATE)

        setUpToolBar()

        openHomePage()

        var previousMenuItem: MenuItem? = null
        previousMenuItem?.isCheckable=true
        previousMenuItem?.isChecked=true

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@HomePage,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        txtNameDrawer.text = sharedPreferences.getString("name","NO NAME")
        txtNumberDrawer.text = "(+91) "+sharedPreferences.getString("mobile_number","NO NUMBER")

        navigationLayout.setNavigationItemSelectedListener {

            if(previousMenuItem != null)
            {
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true

            if(it.itemId == R.id.logout)
            {
                it.isCheckable = false
                it.isChecked = false
            }
            previousMenuItem = it

            when(it.itemId)
            {
                R.id.allRestaurants ->{
                    openHomePage()
                    drawerLayout.closeDrawers()
                }
                R.id.favorite ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FavoriteFragment())
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favorites"
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Profile"
                }
                R.id.orderHistory ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, OrderHistoryFragment())
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Order History"
                }
                R.id.faqs ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FAQFragment())
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="FAQs"
                }
                R.id.logout ->{
//                    Toast.makeText(this@HomePage,"You clicked Logout Button",Toast.LENGTH_SHORT).show()

                    val dailog = AlertDialog.Builder(this@HomePage)
                    dailog.setTitle("Confirmation")
                    dailog.setMessage("Are you sure you want to logout?")
                    dailog.setPositiveButton("Yes"){
                            text,listener ->
                        sharedPreferences.edit().clear().apply()
                        ActivityCompat.finishAffinity(this@HomePage)
                    }
                    dailog.setNegativeButton("Cancel"){
                            text,listener ->
                    }
                    dailog.create()
                    dailog.show()
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id == android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun setUpToolBar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openHomePage()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, HomePageFragment()).commit()
        supportActionBar?.title = "Home Page"
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag)
        {
            !is HomePageFragment -> {
                openHomePage()
            }

            else -> super.onBackPressed()
        }

        drawerLayout.closeDrawers()
    }
}