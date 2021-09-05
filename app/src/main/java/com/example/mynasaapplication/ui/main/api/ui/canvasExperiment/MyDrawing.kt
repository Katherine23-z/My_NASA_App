package com.example.mynasaapplication.ui.main.api.ui.canvasExperiment

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.graphics.toRectF

class MyDrawing(context: Context) : View(context) {
    private lateinit var mPaint: Paint
    private val myText = "Drawing"

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.WHITE)
        //drawRowsAndColumns(canvas)
        drawTextOnPath(canvas)
    }

    private fun drawRowsAndColumns(canvas: Canvas?) {
        mPaint = Paint()
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 1F


        val h = this.height
        val w = this.width
        val cellSize = 30

        for (r in 0..h / cellSize) {
            val y = r * cellSize
            canvas?.drawLine(0F, y.toFloat(), w.toFloat(), y.toFloat(), mPaint)
        }

        for (c in 0..w / cellSize) {
            val x = c * cellSize
            canvas?.drawLine(x.toFloat(), 0F, x.toFloat(), h.toFloat(), mPaint)
        }

        val r = (h / cellSize) + 1
        mPaint.isAntiAlias = true
        for (i in 1..r step 2) {
            val xy = cellSize * i
            canvas?.drawCircle(xy.toFloat(), xy.toFloat(), cellSize.toFloat(), mPaint)
        }
        mPaint.isAntiAlias = false

    }

    private fun drawTextOnPath(canvas: Canvas?) {
        mPaint = Paint()
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 20F
        mPaint.alpha = 25
        mPaint.textSize = 300F

        val h = this.height
        val w = this.width
        val textWidth = mPaint.measureText(myText)
        val l = (w / 2) - (textWidth / 2)
        val t = (h / 2)
        val r = l + textWidth
        val b = t + h / 4

        val rect = Rect(l.toInt(), t, r.toInt(), b)
        canvas?.drawRect(rect, mPaint)

        val path = Path()
        path.addArc(rect.toRectF(), 180F, 180F)
        mPaint.alpha = 75
        canvas?.drawArc(rect.toRectF(), 180F, 180F, false, mPaint)
        mPaint.alpha = 225
        canvas?.drawTextOnPath(myText, path, 0F, 0F, mPaint)
    }

}