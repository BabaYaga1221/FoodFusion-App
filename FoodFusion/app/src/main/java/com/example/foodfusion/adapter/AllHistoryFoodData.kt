package com.example.foodfusion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.R
import com.example.foodfusion.model.FoodDetails

class AllHistoryFoodData(val context: Context, val itemList:ArrayList<FoodDetails>):RecyclerView.Adapter<AllHistoryFoodData.AllDataViewHolder>() {

    class AllDataViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val txtFoodName:TextView = view.findViewById(R.id.historyFoodName)
        val txtFoodPrice:TextView = view.findViewById(R.id.historyFoodPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_food_history_recycler_view,parent,false)
        return AllDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllDataViewHolder, position: Int) {
        val foodDetails = itemList[position]
//        Toast.makeText(context,"I am in All History Food Data", Toast.LENGTH_SHORT).show()
        holder.txtFoodName.text = foodDetails.foodName
        holder.txtFoodPrice.text = foodDetails.foodPrice
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}


