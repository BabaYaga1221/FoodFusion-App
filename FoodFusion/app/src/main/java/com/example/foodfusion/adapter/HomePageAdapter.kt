package com.example.foodfusion.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodfusion.R
import com.example.foodfusion.activity.FoodList
import com.example.foodfusion.database.FoodDatabase
import com.example.foodfusion.database.FoodEntity
import com.example.foodfusion.model.Food
import com.squareup.picasso.Picasso

class HomePageAdapter(val context:Context,val itemList:ArrayList<Food>):RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder>(){

    inner class  DBAsyncTask(val context:Context,val foodEntity: FoodEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, FoodDatabase::class.java, "foods-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {

            when (mode) {
                1 -> {

                    val food: FoodEntity? = db.foodDao().getFoodById(foodEntity.food_id.toString())
                    db.close()
                    return food != null
                }
                2 -> {
                    db.foodDao().insertFood(foodEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.foodDao().deleteFood(foodEntity)
                    db.close()
                    return true
                }

            }
            return false
        }
    }

    class HomePageViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val foodName: TextView = view.findViewById(R.id.txtViewFoodName)
        val foodPrice: TextView = view.findViewById(R.id.txtViewPrice)
        val foodRating: TextView = view.findViewById(R.id.txtFoodRating)
        val imgFood: ImageView = view.findViewById(R.id.imgRecycleHomePage)
        val imgFoodLike: ImageView = view.findViewById(R.id.foodLike)
        val foodListView: CardView = view.findViewById(R.id.bookListCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homepage_recycler_view,parent,false)
        return HomePageViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.food_fusion_prefs),Context.MODE_PRIVATE)
        var food= itemList[position]
        holder.foodName.text = food.foodName
        holder.foodPrice.text = food.foodPriceForOne+"/person"
        holder.foodRating.text= food.foodRating
        Picasso.get().load(food.imgFoodItem).error(R.drawable.ic_action_food).into(holder.imgFood)

        val foodEntity = FoodEntity(
            food.foodId,
            food.foodName,
            food.foodPriceForOne,
            food.foodRating,
            food.imgFoodItem,
            food.isLiked
        )

        if (DBAsyncTask(context,foodEntity,1).execute().get()) {
            holder.imgFoodLike.setImageResource(R.drawable.ic_action_likefill)
        } else {
            holder.imgFoodLike.setImageResource(R.drawable.ic_action_likeunfill)
        }

        holder.imgFoodLike.setOnClickListener{
            food.isLiked = !food.isLiked
            if(!DBAsyncTask(context,foodEntity,1).execute().get())
            {
                val async = DBAsyncTask(context,foodEntity,2).execute()
                val result = async.get()
                if(result)
                {
                    Toast.makeText(context,"Food added to favourites", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Some Error Occurred!!", Toast.LENGTH_SHORT).show()
                }
                holder.imgFoodLike.setImageResource(R.drawable.ic_action_likefill)
                sharedPreferences.edit().putBoolean("liked",!sharedPreferences.getBoolean("liked",true)).apply()
                sharedPreferences.edit().putString("food_id",food.foodId).apply()
                val bounceAnimator = ObjectAnimator.ofFloat(holder.imgFoodLike, "translationY", 0f, -30f, 0f)
                bounceAnimator.duration = 500
                bounceAnimator.interpolator = BounceInterpolator()
                bounceAnimator.start()
            }
            else{
                holder.imgFoodLike.setImageResource(R.drawable.ic_action_likeunfill)
                val async = DBAsyncTask(context,foodEntity,3).execute()
                val result = async.get()
                if(result)
                {
                    Toast.makeText(context,"Food removed from favourites", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Some Error Occurred!!", Toast.LENGTH_SHORT).show()
                }
                sharedPreferences.edit().putBoolean("liked",!sharedPreferences.getBoolean("liked",false)).apply()
                val bounceAnimator = ObjectAnimator.ofFloat(holder.imgFoodLike, "translationY", 0f, -30f, 0f)
                bounceAnimator.duration = 500
                bounceAnimator.interpolator = BounceInterpolator()
                bounceAnimator.start()
            }
        }

        holder.foodListView.setOnClickListener{
            val intent = Intent(context,FoodList::class.java)
//            Toast.makeText(context,"I am in HomePage Adapter",Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putString("foodName",food.foodName).apply()
            sharedPreferences.edit().putString("id",food.foodId).apply()
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}