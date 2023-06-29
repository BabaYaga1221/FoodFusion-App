package com.example.foodfusion.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodfusion.R
import com.example.foodfusion.adapter.FavouriteRecyclerAdapter
import com.example.foodfusion.database.FoodDatabase
import com.example.foodfusion.database.FoodEntity

class FavoriteFragment : Fragment() {

    lateinit var recycleFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    var dbFoodList = listOf<FoodEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        recycleFavourite = view.findViewById(R.id.recycleFavourites)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressbar)

        layoutManager = LinearLayoutManager(activity)

        dbFoodList = RetrieveFavourites(activity as Context).execute().get()

        if(activity != null)
        {
            progressLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context,
                dbFoodList as ArrayList<FoodEntity>
            )
            recycleFavourite.adapter = recyclerAdapter
            recycleFavourite.layoutManager = layoutManager
        }
        return view
    }

    class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<FoodEntity>>(){
        override fun doInBackground(vararg params: Void?): List<FoodEntity> {
            val db = Room.databaseBuilder(context, FoodDatabase::class.java,"foods-db").build()

            return db.foodDao().getAllFoods()
        }
    }
}