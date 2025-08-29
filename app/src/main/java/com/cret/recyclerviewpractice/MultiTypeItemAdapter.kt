package com.cret.recyclerviewpractice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cret.recyclerviewpractice.databinding.ItemImageBinding
import com.cret.recyclerviewpractice.databinding.ItemTextBinding
import com.cret.recyclerviewpractice.databinding.ItemTextImageBinding

class MultiTypeItemAdapter(
    private val items: MutableList<UiItem>,
    private val onItemClick: ((UiItem) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init { setHasStableIds(true) }

    companion object {
        private const val TEXT_TYPE = 0
        private const val IMAGE_TYPE = 1
        private const val TEXT_IMAGE_TYPE = 2
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
            TEXT_TYPE -> TextItemViewHolder(ItemTextBinding.inflate(inflate, parent, false))
            IMAGE_TYPE -> ImageItemViewHolder(ItemImageBinding.inflate(inflate, parent, false))
            TEXT_IMAGE_TYPE -> TextImageItemViewHolder(ItemTextImageBinding.inflate(inflate, parent, false))
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
                holder.image.setBackgroundResource((item as TextImageItem).imageResId)
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
        is TextItem -> TEXT_TYPE
        is ImageItem -> IMAGE_TYPE
        is TextImageItem -> TEXT_IMAGE_TYPE
    }

    fun addItem(item: UiItem): Int {
        items.add(item)
        val position = items.lastIndex
        notifyItemInserted(position)
        return position
    }

    // B리스트에서 REMOVE 버튼 클릭 시 첫 번째 아이템 삭제
    fun removeFirstItem(): UiItem? {
        return if (items.isNotEmpty()) {
            val removedItem = items.removeAt(0)
            notifyItemRemoved(0)
            removedItem
        } else null
    }

    // A리스트에서 선택한 아이템 삭제
    fun removeItem(item: UiItem): Int {
        val position = items.indexOf(item)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
        return position
    }

    fun getItems(): List<UiItem> = items
}
