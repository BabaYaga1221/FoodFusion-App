package com.example.foodfusion.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.R
import com.example.foodfusion.adapter.FAQAdapter
import com.example.foodfusion.model.QnA


class FAQFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FAQAdapter
    var itemList = arrayListOf<QnA>(
        QnA("Q.1 What is Food Fusion?",
            "Food Fusion is a mobile app that allows users to conveniently order food from various restaurants and cafes in their area, as well as provide reviews and ratings for the food and services they experience."),
        QnA("Q.2 How can I download Food Fusion?",
            "You can download the Food Fusion app from the Apple App Store for iOS devices or the Google Play Store for Android devices. Simply search for Food Fusion and follow the installation instructions."),
        QnA("Q.3 How do I create an account on Food Fusion?",
            "To create an account on Food Fusion, open the app and click on the Sign Up button. You will be prompted to enter your personal details, such as your name, email address, and phone number. Once you provide the required information, you can set a password and complete the registration process."),
        QnA("Q.4 Can I order food from any restaurant using Food Fusion?",
            "Food Fusion partners with a wide range of restaurants and cafes. However, the availability of restaurants may vary depending on your location. The app will show you the list of available restaurants in your area once you enter your delivery address."),
        QnA("Q.5 How can I place an order through Food Fusion?",
            "Once you have created an account and logged in, you can browse through the list of restaurants or use the search function to find a specific eatery. Select the desired items from the menu, customize your order if needed, and proceed to the checkout. Provide your delivery address and payment details to complete the order."),
        QnA("Q.6 What payment methods are accepted on Food Fusion?",
            "Food Fusion accepts various payment methods, including credit cards, debit cards, and digital wallets such as Apple Pay and Google Pay. The available payment options may vary depending on your location."),
        QnA("Q.7 How long does it take to deliver the food?",
            "The delivery time depends on several factors, including the restaurant's preparation time, distance from the restaurant to your location, and current order volume. Food Fusion provides an estimated delivery time for each order, which you can view before placing your order."),
        QnA("Q.8 Can I track the status of my order?",
            "Yes, Food Fusion offers a real-time order tracking feature. Once your order is confirmed and the restaurant begins preparing your food, you will be able to track its progress within the app. You can see when the food is being cooked, packed, and out for delivery."),
        QnA("Q.9 Can I cancel or modify my order?",
            "Food Fusion allows users to cancel or modify their orders, but it depends on the specific restaurant's policies and the stage of the order. You can find the option to cancel or modify your order within the app. However, if the order is already being prepared or out for delivery, these changes may not be possible."),
        QnA("Q.10 How can I leave a review or rating for a restaurant on Food Fusion?",
            "After receiving your food, you will have the opportunity to rate and review the restaurant on Food Fusion. Open the app, go to your order history, and select the order you want to review. You can then provide a rating and leave a detailed review about your experience with the restaurant."),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_f_a_q, container, false)

        recyclerView = view.findViewById(R.id.recycleViewQnA)
        layoutManager  = LinearLayoutManager(activity)

        recyclerAdapter = FAQAdapter(activity as Context, itemList)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = layoutManager

        return view
    }
}