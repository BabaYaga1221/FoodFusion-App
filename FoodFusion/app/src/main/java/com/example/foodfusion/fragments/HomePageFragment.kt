package com.example.foodfusion.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.model.Food
import com.example.foodfusion.adapter.HomePageAdapter
import com.example.foodfusion.R
import com.example.foodfusion.util.ConnectionManager
import org.json.JSONException


class HomePageFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: HomePageAdapter
    lateinit var progressBar: RelativeLayout
    lateinit var progressBarHomePage: ProgressBar
    var foodInfoList = arrayListOf<Food>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.recycleView)
        layoutManager = LinearLayoutManager(activity)

        progressBar = view.findViewById(R.id.progressBar)
        progressBarHomePage = view.findViewById(R.id.progressBarHomePage)

        progressBar.visibility = View.VISIBLE

        if(ConnectionManager().checkConnectivity(activity as Context))
        {
            val queue = Volley.newRequestQueue(activity as Context)

            val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

            val jsonRequest = object : JsonObjectRequest(Method.GET, url, null,
                Response.Listener {
                    try {
                        print("Response is $it")
                        progressBar.visibility = View.GONE
                        val jsonData = it.getJSONObject("data")
                        val success = jsonData.getBoolean("success")
                        if (success) {
                            val data = jsonData.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val foodJSONObject = data.getJSONObject(i)
                                val foodObject = Food(
                                    foodJSONObject.optString("id").toString(),
                                    foodJSONObject.optString("name").toString(),
                                    foodJSONObject.optString("rating").toString(),
                                    foodJSONObject.optString("cost_for_one").toString(),
                                    foodJSONObject.optString("image_url").toString(),
                                    false
                                )

                                foodInfoList.add(foodObject)
                                recyclerAdapter = HomePageAdapter(activity as Context, foodInfoList)
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
        }
        else
        {
            val dailog = AlertDialog.Builder(activity as Context)
            dailog.setTitle("Error")
            dailog.setMessage("Internet Connection not Found")
            dailog.setPositiveButton("Open Settings") { text, listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dailog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dailog.create()
            dailog.show()
        }

        return view
    }
}