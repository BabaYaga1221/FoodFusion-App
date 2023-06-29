package com.example.foodfusion.model

data class Food(
    val foodId:String,
    val foodName:String,
    val foodRating:String,
    val foodPriceForOne:String,
    val imgFoodItem:String,
    var isLiked:Boolean
)