package com.example.foodfusion.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.foodfusion.adapter.FoodListAdapter
import com.example.foodfusion.adapter.OrderHistoryAdapter
import com.example.foodfusion.model.FoodDetails
import com.example.foodfusion.model.FoodList
import com.example.foodfusion.model.HistoryData
import org.json.JSONException

class OrderHistoryFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: OrderHistoryAdapter
    lateinit var progressBar: RelativeLayout
    lateinit var progressBarHistoryFragment: ProgressBar
    lateinit var sharedPreferences: SharedPreferences
    var foodItemList= arrayListOf<HistoryData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.food_fusion_prefs),
            Context.MODE_PRIVATE
        )

        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.recycleViewHistory)
        layoutManager = LinearLayoutManager(activity)
        progressBar = view.findViewById(R.id.progressBarHistory)
        progressBarHistoryFragment = view.findViewById(R.id.progressBarHistoryPage)
        progressBar.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(activity as Context)

        val userId = sharedPreferences.getString("user_id","NULL")

        val url = "http://13.235.250.119/v2/orders/fetch_result/$userId"

        val jsonRequest = object:JsonObjectRequest(Method.GET,url,null,Response.Listener {
            try{
            progressBar.visibility = View.GONE
            val jsonData = it.getJSONObject("data")
            val success = jsonData.getBoolean("success")
            if (success) {
                val successData = jsonData.getJSONArray("data")
                for(index in 0 until successData.length()) {
                    val data = successData.getJSONObject(index)

                    val foodItems = data.getJSONArray("food_items")
                    val foodDetailList = ArrayList<FoodDetails>()
                    for (indexFood in 0 until foodItems.length()) {
                        val fooddata = foodItems.getJSONObject(indexFood)
                        val foodDetailsTemp = FoodDetails(
                            fooddata.getString("name"),
                            fooddata.getString("cost")
                        )
                        foodDetailList.add(foodDetailsTemp)
                    }
                    val tempData = HistoryData(
                        data.getString("restaurant_name"),
                        data.getString("order_placed_at"),
                        foodDetailList
                    )
                    foodItemList.add(tempData)
                }
                recyclerAdapter = OrderHistoryAdapter(activity as Context, foodItemList)
                recyclerView.adapter = recyclerAdapter
                recyclerView.layoutManager = layoutManager
            } else {
                Toast.makeText(
                    activity as Context,
                    "Some Error Occurred - status",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(
            activity as Context,
            "Some Error Occurred - JSON ${e.message}",
            Toast.LENGTH_LONG
            ).show()
        }
        },Response.ErrorListener {
            if (activity != null) {
                Toast.makeText(
                    activity as Context,
                    "Volley Error Occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "fc39dc6d113be8"
                return headers
            }
        }
        queue.add(jsonRequest)
        return view
    }

}