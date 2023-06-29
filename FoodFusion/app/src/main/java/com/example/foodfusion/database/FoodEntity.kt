package com.example.foodfusion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Foods")
data class FoodEntity(
    @PrimaryKey val food_id:String,
    @ColumnInfo(name = "food_name") val foodName:String,
    @ColumnInfo(name = "food_price") val foodPrice:String,
    @ColumnInfo(name = "food_rating") val foodRating:String,
    @ColumnInfo(name = "food_image") val foodImg:String,
    @ColumnInfo(name = "food_liked") val foodLiked:Boolean
)