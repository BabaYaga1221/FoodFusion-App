package com.example.foodfusion.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {

    @Insert
    fun insertFood(foodEntity: FoodEntity)

    @Delete
    fun deleteFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM Foods")
    fun getAllFoods():List<FoodEntity>

    @Query("SELECT * FROM Foods WHERE food_id = :foodId")
    fun getFoodById(foodId:String):FoodEntity
}