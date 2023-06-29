package com.example.foodfusion.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodfusion.R
import com.example.foodfusion.activity.OrderPlaced
import org.json.JSONArray
import org.json.JSONObject
import java.util.zip.Inflater

class CartAdapter(val context: Context,val itemList:ArrayList<HashMap<String,String>>,val btnProceed: Button,val totalMoney:Int,val sharedPreferences: SharedPreferences):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val txtFoodName:TextView = view.findViewById(R.id.cartFoodName)
        val txtFoodPrice:TextView = view.findViewById(R.id.cartFoodPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_recycler_view,parent,false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartFood = itemList[position]
        holder.txtFoodName.text = cartFood["food_name"]
        holder.txtFoodPrice.text = cartFood["food_price"]
        btnProceed.text = "Place Order(Total: Rs.$totalMoney)"

        btnProceed.setOnClickListener{
            val queue = Volley.newRequestQueue(context)
        val url = "http://13.235.250.119/v2/place_order/fetch_result/"

        var jsonParams = JSONObject()
            val id = sharedPreferences.getString("id", "NULL")
            val user_id = sharedPreferences.getString("user_id", "NULL")
        jsonParams.put("user_id", user_id)
        jsonParams.put("restaurant_id",id)
        jsonParams.put("total_cost",totalMoney.toString())

        var jsonArray = JSONArray()
        for (i in 0 until itemList.size) {
            val temp = JSONObject()
            temp.put("food_item_id", itemList[i]["food_id"])
            jsonArray.put(temp)
        }

        jsonParams.put("food", jsonArray)

        val jsonRequest =
            object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {
                val data = it.getJSONObject("data")
                println("JSONPARAMS :$jsonParams")
                if (data.getBoolean("success")) {
//                    Toast.makeText(context,"Your Order Placed Successfully",Toast.LENGTH_SHORT).show()
                    val intent = Intent(context,OrderPlaced::class.java)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context,"error - ${data.getString("errorMessage")}",Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                if (context != null) {
                    Toast.makeText(
                        context,
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
    }

    override fun getItemCount(): Int {
//        Toast.makeText(context, "Item count: " + itemList.size, Toast.LENGTH_SHORT).show()
        return itemList.size
    }

}