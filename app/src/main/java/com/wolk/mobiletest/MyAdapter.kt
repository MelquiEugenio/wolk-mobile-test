package com.wolk.mobiletest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*


class MyAdapter(val arrayList: ArrayList<Model>, val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model :Model) {

            itemView.repo_title.text = model.title

            if (model.description != "null")
                itemView.repo_description.text = model.description
            else
                itemView.repo_description.text = ""

            if (model.language != "null")
                itemView.repo_language.text = model.language
            else
                itemView.repo_language.text = ""

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(arrayList[position])

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}