package com.cret.recyclerviewpractice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cret.recyclerviewpractice.databinding.ItemImageBinding
import com.cret.recyclerviewpractice.databinding.ItemTextBinding
import com.cret.recyclerviewpractice.databinding.ItemTextImageBinding

class MultiTypeItemAdapter(
    private val items: MutableList<UiItem>,
    private val onItemClick: ((UiItem) -> Unit)? = null,
) : ListAdapter<UiItem, RecyclerView.ViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<UiItem>() {
            override fun areItemsTheSame(
                oldItem: UiItem,
                newItem: UiItem
            ): Boolean =
                (oldItem.id == newItem.id) && (oldItem::class == newItem::class)

            override fun areContentsTheSame(
                oldItem: UiItem,
                newItem: UiItem
            ): Boolean =
                oldItem == newItem
        }
    }


    class TextItemViewHolder(binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {
        val text = binding.textOnly
    }
    class ImageItemViewHolder(binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageOnly
    }
    class TextImageItemViewHolder(binding: ItemTextImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val text = binding.text
        val image = binding.image
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        Log.d("RecyclerViewTest", "onAttachedToRecyclerView")

        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_text -> TextItemViewHolder(ItemTextBinding.inflate(inflate, parent, false))
            R.layout.item_image -> ImageItemViewHolder(ItemImageBinding.inflate(inflate, parent, false))
            R.layout.item_text_image -> TextImageItemViewHolder(ItemTextImageBinding.inflate(inflate, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (onItemClick != null) {
            holder.itemView.setOnClickListener { onItemClick.invoke(item) }
        } else {
            holder.itemView.setOnClickListener(null)
        }
        
        when(holder) {
            is TextItemViewHolder -> holder.text.text = (item as TextItem).text
            is ImageItemViewHolder -> holder.image.setBackgroundResource((item as ImageItem).imageResId)
            is TextImageItemViewHolder -> {
                holder.text.text = (item as TextImageItem).text
                holder.image.setImageResource((item as TextImageItem).imageResId)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        Log.d("RecyclerViewTest", "onViewAttachedToWindow")

        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        Log.d("RecyclerViewTest", "onViewDetachedFromWindow")

        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        Log.d("RecyclerViewTest", "onViewRecycled")

        super.onViewRecycled(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        Log.d("RecyclerViewTest", "onDetachedFromRecyclerView")

        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemId(position: Int): Long = items[position].id.toLong()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is TextItem -> R.layout.item_text
        is ImageItem -> R.layout.item_image
        is TextImageItem -> R.layout.item_text_image
    }
}
