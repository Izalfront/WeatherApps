package com.example.weatherapps.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.example.weatherapps.R

class CustomCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var backgroundColor: Int = Color.WHITE
    private var cornerRadius: Float = 0f

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCardView,
            0, 0
        ).apply {
            try {
                backgroundColor = getColor(R.styleable.CustomCardView_backgroundColor, Color.WHITE)
                cornerRadius = getDimension(R.styleable.CustomCardView_cornerRadius, 0f)
            } finally {
                recycle()
            }
        }

        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint().apply {
            color = backgroundColor
            isAntiAlias = true
        }
        val rect = canvas.clipBounds
        canvas.drawRoundRect(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.bottom.toFloat(),
            cornerRadius,
            cornerRadius,
            paint
        )
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        backgroundColor = color
        invalidate()
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }
}