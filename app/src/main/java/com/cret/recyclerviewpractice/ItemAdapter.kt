package com.cret.recyclerviewpractice

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cret.recyclerviewpractice.databinding.ItemListBinding

class ItemAdapter(
    private val items: MutableList<Item>,
    private val onItemClick: ((Item) -> Unit)? = null
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textView
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        Log.d("RecyclerViewTest", "onAttachedToRecyclerView")

        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.text
        
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
        Log.d("RecyclerViewTest", "onViewAttachedToWindow")

        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
        Log.d("RecyclerViewTest", "onViewDetachedFromWindow")

        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        Log.d("RecyclerViewTest", "onViewRecycled")

        super.onViewRecycled(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        Log.d("RecyclerViewTest", "onDetachedFromRecyclerView")

        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeFirstItem(): Item? {
        return if (items.isNotEmpty()) {
            val removedItem = items.removeAt(0)
            notifyItemRemoved(0)
            removedItem
        } else null
    }

    fun getItems(): List<Item> = items.toList()
}
