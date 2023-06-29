package com.example.foodfusion.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foodfusion.R
import org.w3c.dom.Text

class ProfileFragment : Fragment() {

    lateinit var txtProfileName:TextView
    lateinit var txtProfileNumber:TextView
    lateinit var txtProfileEmail:TextView
    lateinit var txtProfileLocation:TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        txtProfileName = view.findViewById(R.id.profileName)
        txtProfileNumber = view.findViewById(R.id.profileNumber)
        txtProfileEmail = view.findViewById(R.id.profileEmail)
        txtProfileLocation = view.findViewById(R.id.profileAddress)

        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.food_fusion_prefs),Context.MODE_PRIVATE)

        txtProfileName.text = sharedPreferences.getString("name","NO NAME")
        txtProfileNumber.text = sharedPreferences.getString("mobile_number","NO NUMBER")
        txtProfileEmail.text = sharedPreferences.getString("email","NO EMAIL")
        txtProfileLocation.text = sharedPreferences.getString("address","NO ADDRESS")

        return view
    }
}