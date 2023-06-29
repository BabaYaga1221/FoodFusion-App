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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.R
import com.example.foodfusion.adapter.FoodListAdapter
import com.example.foodfusion.model.FoodList
import org.json.JSONException

class FoodListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FoodListAdapter
    lateinit var progressBar: RelativeLayout
    lateinit var progressBarFoodListFragment: ProgressBar
    lateinit var sharedPreferences: SharedPreferences
    var foodListInfo = arrayListOf<FoodList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.food_fusion_prefs),
            Context.MODE_PRIVATE
        )

        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.recycleViewFoodList)
        layoutManager = LinearLayoutManager(activity)

        progressBar = view.findViewById(R.id.progressBarFoodList)
        progressBarFoodListFragment = view.findViewById(R.id.progressBarFoodListFragment)
//        imgHeart = view.findViewById(R.id.foodLike)
        progressBar.visibility = View.VISIBLE

//        Toast.makeText(context,"This is Fragment class",Toast.LENGTH_SHORT).show()

        val queue = Volley.newRequestQueue(activity as Context)

//        Toast.makeText(context,"I am in FoodList Fragment",Toast.LENGTH_SHORT).show()


        val id = sharedPreferences.getString("id", "null")

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/$id"

        val jsonRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener {

            try {
                progressBar.visibility = View.GONE
                val jsonData = it.getJSONObject("data")
                val success = jsonData.getBoolean("success")
                if (success) {
                    val data = jsonData.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val foodJSONObject = data.getJSONObject(i)
                        val foodObject = FoodList(
                            foodJSONObject.getString("id").toString(),
                            foodJSONObject.getString("name").toString(),
                            foodJSONObject.getString("cost_for_one").toString(),
                            foodJSONObject.getString("restaurant_id").toString(),
                        )

                        foodListInfo.add(foodObject)
                        recyclerAdapter = FoodListAdapter(activity as Context,foodListInfo,view.findViewById(R.id.btnCart))
                        recyclerView.adapter = recyclerAdapter
                        recyclerView.layoutManager = layoutManager
                    }
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
        }, Response.ErrorListener {
            if (activity != null) {
                Toast.makeText(
                    activity as Context,
                    "Volley Error Occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
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