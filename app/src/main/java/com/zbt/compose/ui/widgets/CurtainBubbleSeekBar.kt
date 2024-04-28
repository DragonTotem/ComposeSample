package com.zbt.compose.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Date: 2024/4/24 下午12:13
 * Version: 1.0
 * Desc: CurtainBubbleSeekBar
 * 1、根据paddingTop来显示文字，和向下显示seekbar
 * 2、paddingTop部分不处理触摸事件
 */
class CurtainBubbleSeekBar(context: Context, attrs: AttributeSet? = null) :
    AppCompatSeekBar(context, attrs) {

    private val TAG = "CurtainBubbleSeekBar"

    private val textPaint = Paint().apply {
        isAntiAlias = true
        textSize = 20f
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
    }

    private val rectPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private val triangleWidth = 10
    private val radius = 6f
    private val rectHeight = 26
    private val rectWidth = 50
    private var userTouched = false

    private var widthActualProgress = 0
    private var rangeForProgress = 100

//    init {
//        // 滑块底部 背景样式 （false为透明 ）
//        splitTrack = false
//        // 子View的drawable状态的改变跟随父View的状态的改变
//        isDuplicateParentStateEnabled = true
//    }

    @SuppressWarnings("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        Log.d(TAG, "onTouchEvent event: ${event.action} x: ${event.x}  y: ${event.y}")
        if (event.action == MotionEvent.ACTION_DOWN && event.y < 36) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                userTouched = true
                if (isScrollContainer) {
                    // SeekBar 由于是嵌套在RecyclerView中不会主动更新Progress
                    progress =
                        ((event.x - paddingStart) * rangeForProgress / widthActualProgress + min).roundToInt()
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> userTouched = false
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (userTouched) {
            val position = calculatePositionXY()
            canvas.run {
                drawTextBackground(position)
                drawText(position)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthActualProgress = measuredWidth - paddingStart - paddingEnd
    }

    /**
     * 绘制文字的背景
     */
    private fun Canvas.drawTextBackground(position: Pair<Float, Float>) {
        // 先画圆角矩形
        drawRoundRect(
            position.first - rectWidth / 2,
            position.second - triangleWidth - rectHeight,
            position.first + rectWidth / 2,
            position.second - triangleWidth,
            radius,
            radius,
            rectPaint
        )
        // 再画三角形
        val path = Path().apply {
            moveTo(position.first, position.second)
            lineTo(position.first - triangleWidth / 2, position.second - triangleWidth)
            lineTo(position.first + triangleWidth / 2, position.second - triangleWidth)
            close()
        }
        drawPath(path, rectPaint)
    }

    private fun Canvas.drawText(position: Pair<Float, Float>) =
        drawText(
            "$progress°",
            position.first,
            position.second - triangleWidth - rectHeight / 2 + 6,
            textPaint
        )

    /**
     * 计算Thumb的顶点
     */
    private fun calculatePositionXY(): Pair<Float, Float> {
        val positionX = widthActualProgress * (progress - min) / rangeForProgress + paddingStart
        val pointY = (measuredHeight - thumb.intrinsicHeight) / 2
        // 需要找到Thumb的顶点y
        val positionY = max(paddingTop, pointY)
        return positionX.toFloat() to positionY.toFloat()
    }

    fun setMaxAndMin(max: Int, min: Int) {
        rangeForProgress = max - min
        this.max = max
        this.min = min
    }
}