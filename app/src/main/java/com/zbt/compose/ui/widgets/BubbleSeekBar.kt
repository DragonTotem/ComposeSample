package com.zbt.compose.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import kotlin.math.max

/**
 * create on 2024/2/23 18:03
 * description: 自定义显示当前百分比气泡的 SeekBar，支持旋转 270 度，90 度 和 180 度 暂时没有测试
 * 目前只支持通过 paddingTop 来偏移气泡的位置
 */
class BubbleSeekBar(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatSeekBar(context, attrs) {

    private val triangleHeight = 7f

    private val textPaint = Paint().apply {
        isAntiAlias = true
        textSize = 18f
        color = Color.BLACK
    }

    private val rectPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private var isUserTouch = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> isUserTouch = true
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> isUserTouch = false
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isUserTouch) {
            canvas.save()
            val (positionX, positionY) = getCentralXY()
            canvas.rotate(
                360f - rotation,
                positionX.toFloat(),
                positionY.toFloat() + thumb.intrinsicHeight / 2f
            )
            drawRectAngle(canvas)
            drawTriangle(canvas)
            drawText(canvas)
            canvas.restore()
        }
    }

    private fun drawTriangle(canvas: Canvas) {
        val (positionX, positionY) = getCentralXY()
        val halfTriangleWith = 5f
        val trianglePath = Path().apply {
            moveTo(positionX.toFloat(), positionY.toFloat())
            lineTo(positionX - halfTriangleWith, positionY - triangleHeight)
            lineTo(positionX + halfTriangleWith, positionY - triangleHeight)
            close()
        }
        canvas.drawPath(trianglePath, rectPaint)
    }

    private fun drawRectAngle(canvas: Canvas) {
        val rectHeight = 30f
        val rectWidth = 54f
        val radius = 10f
        val (positionX, positionY) = getCentralXY()
        canvas.drawRoundRect(
            positionX - rectWidth / 2,
            positionY - rectHeight - triangleHeight,
            positionX + rectWidth / 2,
            positionY - triangleHeight,
            radius,
            radius,
            rectPaint
        )
    }

    private fun drawText(canvas: Canvas) {
        val text = "$progress%"
        val halfTextWidth = textPaint.measureText(text) / 2
        val (positionX, positionY) = getCentralXY()
        val textBottomDistance = triangleHeight + 9f
        canvas.drawText(text, positionX - halfTextWidth, positionY - textBottomDistance, textPaint)
    }

    private fun getCentralXY(): Pair<Int, Int> {
        val positionX = (measuredWidth - paddingStart - paddingEnd - marginStart - marginEnd) *
                progress / max + paddingStart + marginStart

        val normalEmptyHeight = (measuredHeight - thumb.intrinsicHeight) / 2
        val positionY = max(paddingTop + 3 , normalEmptyHeight)
        return Pair(positionX, positionY)
    }
}