package com.example.unamordida.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unamordida.R



class ItemAdapter(
    private val itemList: ArrayList<Item>,
    private val listener: OnItemClickListener
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val button: Button = itemView.findViewById(R.id.add_button)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
        fun addToCart(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = item.price
        holder.descriptionTextView.text = item.description
        holder.imageView.setImageResource(item.image)

        holder.button.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = itemList.size
}