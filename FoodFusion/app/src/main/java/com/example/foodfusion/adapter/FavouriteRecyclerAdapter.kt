package com.example.foodfusion.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.R
import com.example.foodfusion.activity.FoodList
import com.example.foodfusion.database.FoodEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val foodList:ArrayList<FoodEntity>):RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val foodName: TextView = view.findViewById(R.id.txtViewFoodName)
        val foodPrice: TextView = view.findViewById(R.id.txtViewPrice)
        val foodRating: TextView = view.findViewById(R.id.txtFoodRating)
        val foodImage: ImageView = view.findViewById(R.id.imgRecycleFavourite)
        val foodListCard:CardView = view.findViewById(R.id.foodListCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_recycler_view,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val sharedPreferences = context.getSharedPreferences(R.string.food_fusion_prefs.toString(),Context.MODE_PRIVATE)
        val food = foodList[position]
        holder.foodName.text = food.foodName
        holder.foodPrice.text = food.foodPrice+"/person"
        holder.foodRating.text = food.foodRating
        Picasso.get().load(food.foodImg).error(R.drawable.ic_action_food).into(holder.foodImage)
        holder.foodListCard.setOnClickListener{
            val intent = Intent(context, FoodList::class.java)
            sharedPreferences.edit().putString("foodName",food.foodName).apply()
            sharedPreferences.edit().putString("id",food.food_id).apply()
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

}