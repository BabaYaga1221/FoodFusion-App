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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.R
import com.example.foodfusion.activity.CartPage
import com.example.foodfusion.model.FoodList

class FoodListAdapter(val context:Context, val itemList:ArrayList<FoodList>,val btnCart:Button):RecyclerView.Adapter<FoodListAdapter.FoodListHolderView>() {

    lateinit var sharedPreferences: SharedPreferences
    var foodIdList= arrayListOf<HashMap<String,String>>()
    var totalMoney:Int = 0

    class FoodListHolderView(view: View):RecyclerView.ViewHolder(view)
    {
        val txtSrNumber:TextView = view.findViewById(R.id.FoodListSrNo)
        val txtFoodName:TextView = view.findViewById(R.id.FoodListItem)
        val txtFoodPrice:TextView = view.findViewById(R.id.foodItemPrice)
        val btnAdd:Button = view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListHolderView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_list_recycler_view,parent,false)
        return FoodListHolderView(view)
    }

    override fun onBindViewHolder(holder: FoodListHolderView, position: Int) {
        val foodlist = itemList[position]
        sharedPreferences = context.getSharedPreferences(R.string.food_fusion_prefs.toString(),Context.MODE_PRIVATE)
        holder.txtSrNumber.text = (position+1).toString()
        holder.txtFoodName.text = foodlist.foodName
        holder.txtFoodPrice.text = "Rs. "+foodlist.foodPriceForOne
        holder.btnAdd.setOnClickListener{
            if(holder.btnAdd.text == "Add")
            {
                holder.btnAdd.text = "Remove"
                holder.btnAdd.setBackgroundColor(ContextCompat.getColor(context,R.color.teal_700))
                val hashmap=HashMap<String,String>()
                hashmap["food_id"]=foodlist.foodId
                hashmap["food_name"]=foodlist.foodName
                hashmap["food_price"]=foodlist.foodPriceForOne
                foodIdList.add(hashmap)
                totalMoney += foodlist.foodPriceForOne.toInt()
                Toast.makeText(context,"Your Amount: ${totalMoney}",Toast.LENGTH_SHORT).show()
            }
            else
            {
                holder.btnAdd.text = "Add"
                holder.btnAdd.setBackgroundColor(ContextCompat.getColor(context,R.color.Green))
                val hashmap=HashMap<String,String>()
                hashmap["food_id"]=foodlist.foodId
                hashmap["food_name"]=foodlist.foodName
                hashmap["food_price"]=foodlist.foodPriceForOne
                foodIdList.remove(hashmap)
                totalMoney -= foodlist.foodPriceForOne.toInt()
                Toast.makeText(context,"Your Amount: ${totalMoney}",Toast.LENGTH_SHORT).show()
            }
        }

        btnCart.setOnClickListener {
            if(foodIdList.size == 0)
                Toast.makeText(context,"Your Cart is Empty",Toast.LENGTH_SHORT).show()
            else
            {
            val intent = Intent(context, CartPage::class.java)
                intent.putExtra("foodIdList",foodIdList)
                intent.putExtra("totalMoney",totalMoney)
//                Toast.makeText(context,"This is btnCart in FoodAdapter",Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}