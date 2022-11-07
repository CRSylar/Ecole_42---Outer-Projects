package com.example.ft_hangouts.adapter

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.ContactCardActivity
import com.example.ft_hangouts.R
import com.example.ft_hangouts.User
import com.example.ft_hangouts.users

class ItemAdapter(
    private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: Button = view.findViewById<Button>(R.id.contact_item)
    }

    private val themeKey = "currentTheme"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = users.toList()[position]
        holder.textView.text = item.second.name

        holder.textView.setOnClickListener {
            val context  = holder.view.context
            val intent = Intent(context, ContactCardActivity::class.java)
            intent.putExtra(themeKey, sharedPreferences.getString(themeKey, ""))
            intent.putExtra("User", item.second)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

}

