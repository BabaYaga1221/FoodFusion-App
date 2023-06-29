package com.example.foodfusion.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.R
import com.example.foodfusion.adapter.CartAdapter
import com.example.foodfusion.adapter.FoodListAdapter
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class CartPageFragment(val foodIdList:ArrayList<HashMap<String,String>>,val totalMoney:Int) : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: CartAdapter
    lateinit var progressBar: RelativeLayout
    lateinit var progressBarFoodListFragment: ProgressBar
    lateinit var sharedPreferences: SharedPreferences
    lateinit var cartRestaurentName:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart_page, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.food_fusion_prefs),
            Context.MODE_PRIVATE
        )

        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.recycleViewCartList)
        layoutManager = LinearLayoutManager(activity)

        progressBar = view.findViewById(R.id.progressBarCartList)
        progressBarFoodListFragment = view.findViewById(R.id.progressBarCartListFragment)
        progressBar.visibility = View.GONE
        cartRestaurentName = view.findViewById(R.id.cartRestaurantName)
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.food_fusion_prefs),Context.MODE_PRIVATE)
        cartRestaurentName.text = sharedPreferences.getString("foodName","NULL")
        recyclerAdapter = CartAdapter(activity as Context,foodIdList,view.findViewById(R.id.btnCart),totalMoney,sharedPreferences)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = layoutManager
//        Toast.makeText(context,"This is CartPage Fragment",Toast.LENGTH_SHORT).show()
        return view
    }
}