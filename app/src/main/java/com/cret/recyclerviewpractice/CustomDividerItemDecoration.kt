package com.cret.recyclerviewpractice

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class CustomDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    
    private val divider: Drawable = context.getDrawable(R.drawable.item_divider)!!
    
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        
        val childCount = parent.childCount
        for (i in 0 until childCount -1) { //가장 아래 아이템은 아래 구분선을 가지지 않도록 하기 위함
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}
