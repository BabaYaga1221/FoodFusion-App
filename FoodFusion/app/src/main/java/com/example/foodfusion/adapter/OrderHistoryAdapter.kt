package com.example.foodfusion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.R
import com.example.foodfusion.model.HistoryData
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderHistoryAdapter(val context:Context,val itemList:ArrayList<HistoryData>):RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    lateinit var recyclerAdapter: AllHistoryFoodData

    private var currentOuterPosition = 0

    class OrderHistoryViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val txtRestaurantName:TextView = view.findViewById(R.id.restaurantName)
        val txtHistoryDate:TextView = view.findViewById(R.id.historyDate)
        val recyclerView:RecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_history_recycler_view,parent,false)
        return OrderHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val historyData = itemList[position]
//        Toast.makeText(context,"I am in Order History Adapter",Toast.LENGTH_SHORT).show()
        holder.txtRestaurantName.text = historyData.restaurentName
//        var newDate = ""
//        for(index in 0 until historyData.historyDate.length)
//        {
//            if(historyData.historyDate[index].equals(" "))
//            {
//                break
//            }
//            else{
//                newDate += historyData.historyDate[index]
//            }
//        }
        holder.txtHistoryDate.text = historyData.historyDate.substring(0,8)
        recyclerAdapter = AllHistoryFoodData(context,historyData.foodList)
        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}