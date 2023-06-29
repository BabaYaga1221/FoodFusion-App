package com.example.foodfusion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfusion.R
import com.example.foodfusion.model.QnA

class FAQAdapter(val context: Context, val itemList:ArrayList<QnA>):RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    class FAQViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val question: TextView = view.findViewById(R.id.qnaQuestion)
        val answer: TextView = view.findViewById(R.id.qnaAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq_recycler_view,parent,false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val qna = itemList[position]
        holder.question.text = qna.qnaQuestion
        holder.answer.text = qna.qnaAnswer
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
