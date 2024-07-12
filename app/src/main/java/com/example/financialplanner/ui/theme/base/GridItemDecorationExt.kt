package com.example.financialplanner.ui.theme.base

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val borderWidth: Int, private val borderColor: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = borderWidth.toFloat()
        color = borderColor
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val layoutManager = parent.layoutManager as GridLayoutManager
        val spanCount = layoutManager.spanCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = child.left - params.leftMargin
            val right = child.right + params.rightMargin + borderWidth
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin + borderWidth

            if (i < spanCount) {
                c.drawLine(left.toFloat(), top.toFloat(), right.toFloat(), top.toFloat(), paint)
            }

            if (i % spanCount == 0) {
                c.drawLine(left.toFloat(), top.toFloat(), left.toFloat(), bottom.toFloat(), paint)
            }

            c.drawLine(left.toFloat(), bottom.toFloat(), right.toFloat(), bottom.toFloat(), paint)

            c.drawLine(right.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(borderWidth, borderWidth, borderWidth, borderWidth)
    }
}

