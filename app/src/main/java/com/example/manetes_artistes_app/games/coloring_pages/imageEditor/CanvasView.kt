package com.example.manetesartistes_game.imageEditor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.get
import androidx.core.graphics.set
import com.example.manetesartistes_game.R
import java.util.*

@SuppressLint("ClickableViewAccessibility")
class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private var canvasBitmap: Bitmap? = null
    private var fillColor = Color.WHITE
    private val tolerance = 0.20f

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
                true
            } else {
                false
            }
        }
    }

    fun initCanvas(bitmap: Bitmap){
        this.bitmap = bitmap
        this.canvasBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the updated bitmap on the canvas, centered
        canvasBitmap?.let {
            val left = (width - it.width) / 2f
            val top = (height - it.height) / 2f
            canvas.drawBitmap(it, left, top, null)
        }
    }

    // BFS flood fill algorithm
    private fun floodFill(x: Int, y: Int, targetColor: Int) {
        val bitmap = canvasBitmap ?: return

        if (targetColor == fillColor) return // Skip if target is already filled

        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(Pair(x, y))

        while (queue.isNotEmpty()) {
            val (cx, cy) = queue.remove()

            if (cx !in 0 until bitmap.width || cy !in 0 until bitmap.height) continue

            val currentColor = bitmap[cx, cy]
            if (currentColor == Color.BLACK || !isColorInTolerance(targetColor, currentColor)) continue

            if (!isColorInTolerance(targetColor, currentColor)) continue
            if (Color.alpha(currentColor) == 0) continue // Skip transparent pixels

            bitmap[cx, cy] = fillColor

            queue.add(Pair(cx + 1, cy))
            queue.add(Pair(cx - 1, cy))
            queue.add(Pair(cx, cy + 1))
            queue.add(Pair(cx, cy - 1))
        }
    }

    private fun isColorInTolerance(targetColor: Int, currentColor: Int): Boolean {
        val targetRed = Color.red(targetColor)
        val targetGreen = Color.green(targetColor)
        val targetBlue = Color.blue(targetColor)

        val currentRed = Color.red(currentColor)
        val currentGreen = Color.green(currentColor)
        val currentBlue = Color.blue(currentColor)

        val toleranceValue = (255 * tolerance).toInt()

        return (Math.abs(targetRed - currentRed) <= toleranceValue) &&
                (Math.abs(targetGreen - currentGreen) <= toleranceValue) &&
                (Math.abs(targetBlue - currentBlue) <= toleranceValue)
    }

     fun setFillColor(color: Int){
        fillColor = color
    }
}
