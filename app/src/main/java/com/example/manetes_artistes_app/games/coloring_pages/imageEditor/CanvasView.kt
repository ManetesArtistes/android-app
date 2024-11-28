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

        val width = bitmap.width
        val height = bitmap.height
        val targetColorAlpha = Color.alpha(targetColor)

        // Handle the case where the target color is already filled
        if (targetColor == fillColor) return

        val stack = mutableListOf<Pair<Int, Int>>()
        stack.add(Pair(x, y))

        // The bitmap pixels (assuming it's a mutable bitmap with direct pixel access)
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // Calculate index for 2D coordinates
        fun index(cx: Int, cy: Int) = cy * width + cx

        // Processed pixels tracking
        val processed = BooleanArray(pixels.size)

        while (stack.isNotEmpty()) {
            val (cx, cy) = stack.removeAt(stack.lastIndex)

            // Check bounds and processed pixels
            if (cx !in 0 until width || cy !in 0 until height || processed[index(cx, cy)]) continue

            val currentColor = pixels[index(cx, cy)]

            // Skip if the color is not the target color or if it's a transparent pixel
            if (currentColor != targetColor || Color.alpha(currentColor) == 0) continue

            // Fill the pixel with the desired fill color
            pixels[index(cx, cy)] = fillColor
            processed[index(cx, cy)] = true

            // Add neighbors (right, left, down, up)
            if (cx + 1 < width && !processed[index(cx + 1, cy)] && pixels[index(cx + 1, cy)] == targetColor) {
                stack.add(Pair(cx + 1, cy))
            }
            if (cx - 1 >= 0 && !processed[index(cx - 1, cy)] && pixels[index(cx - 1, cy)] == targetColor) {
                stack.add(Pair(cx - 1, cy))
            }
            if (cy + 1 < height && !processed[index(cx, cy + 1)] && pixels[index(cx, cy + 1)] == targetColor) {
                stack.add(Pair(cx, cy + 1))
            }
            if (cy - 1 >= 0 && !processed[index(cx, cy - 1)] && pixels[index(cx, cy - 1)] == targetColor) {
                stack.add(Pair(cx, cy - 1))
            }
        }

        // Set the updated pixels back to the bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    }



    fun setFillColor(color: Int){
        fillColor = color
    }
}
