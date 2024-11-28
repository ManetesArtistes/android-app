package com.example.manetes_artistes_app.imageEditor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.get
import androidx.core.graphics.set
import java.util.*

@SuppressLint("ClickableViewAccessibility")
class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private var canvasBitmap: Bitmap? = null
    private var fillColor = Color.WHITE
    private var activityBitmapStore: ((Bitmap) -> Unit)? = null


    init {
        bitmap?.let {
            canvasBitmap = it.copy(Bitmap.Config.ARGB_8888, true)
        }

        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = (event.x - (width - canvasBitmap!!.width) / 2).toInt()
                val y = (event.y - (height - canvasBitmap!!.height) / 2).toInt()

                if (x in 0 until canvasBitmap!!.width && y in 0 until canvasBitmap!!.height) {
                    // Only apply flood fill if target is not already color and is not transparent
                    val targetColor = canvasBitmap!![x, y]
                    if (targetColor != fillColor && Color.alpha(targetColor) != 0) {
                        floodFill(x, y, targetColor)
                        invalidate()
                    }
                }

                activityBitmapStore?.invoke(canvasBitmap!!)
                true
            } else {
                false
            }
        }
    }

    fun initCanvas(bitmap: Bitmap, activityBitmapStore: ((Bitmap) -> Unit)? = null) {
        this.activityBitmapStore = activityBitmapStore
        this.bitmap = bitmap
        this.canvasBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvasBitmap?.let {
            val left = (width - it.width) / 2f
            val top = (height - it.height) / 2f
            canvas.drawBitmap(it, left, top, null)
        }
    }

    // BFS flood fill algorithm
    private fun floodFill(x: Int, y: Int, targetColor: Int) {
        val bitmap = canvasBitmap ?: return
        if (targetColor == fillColor) return

        val stack: MutableList<Pair<Int, Int>> = mutableListOf()
        stack.add(Pair(x, y))

        val width = bitmap.width
        val height = bitmap.height
        val targetColorAlpha = Color.alpha(targetColor)

        // Instead of using a separate set to track processed pixels, we will modify the bitmap itself
        val processed = Array(height) { BooleanArray(width) }

        // Process each pixel
        while (stack.isNotEmpty()) {
            val (cx, cy) = stack.removeAt(stack.lastIndex)

            // Skip if out of bounds or already processed
            if (cx !in 0 until width || cy !in 0 until height || processed[cy][cx]) continue

            val currentColor = bitmap[cx, cy]

            // Skip if the color is not the target color or if it's a transparent pixel
            if (currentColor != targetColor || Color.alpha(currentColor) == 0) continue

            // Fill the pixel with the desired fill color
            bitmap[cx, cy] = fillColor
            processed[cy][cx] = true

            // Add neighbors to the stack (right, left, down, up)
            if (cx + 1 < width && !processed[cy][cx + 1] && bitmap[cx + 1, cy] == targetColor) {
                stack.add(Pair(cx + 1, cy))
            }
            if (cx - 1 >= 0 && !processed[cy][cx - 1] && bitmap[cx - 1, cy] == targetColor) {
                stack.add(Pair(cx - 1, cy))
            }
            if (cy + 1 < height && !processed[cy + 1][cx] && bitmap[cx, cy + 1] == targetColor) {
                stack.add(Pair(cx, cy + 1))
            }
            if (cy - 1 >= 0 && !processed[cy - 1][cx] && bitmap[cx, cy - 1] == targetColor) {
                stack.add(Pair(cx, cy - 1))
            }
        }
    }


    fun setFillColor(color: Int){
        fillColor = color
    }
}
