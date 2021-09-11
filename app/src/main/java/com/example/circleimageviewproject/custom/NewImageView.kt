package com.example.circleimageviewproject.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import com.example.circleimageviewproject.R
import kotlin.math.min

class NewImageView : androidx.appcompat.widget.AppCompatImageView {

    companion object {
        const val TAG = "NewImageView"
    }

    enum class Type {
        ROUND, CIRCLE
    }

    private var border = Type.CIRCLE
    private var borderWidth = 0f
    private var borderColor = Color.TRANSPARENT

    private var viewWidth = 0f
    private var viewHeight = 0f
    private var radius = 0f

    private val pathOfRadius = Path()
    private val pathCircle = Path()
    private var borderCorner = 0f

    private val linePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getAttrs(context = context, attrs = attrs)
    }

    private fun getAttrs(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.NewImageView).apply {
            border = if (this.getInt(R.styleable.NewImageView_border, 0) == 0) {
                Type.ROUND
            } else {
                Type.CIRCLE
            }
            borderWidth = getDimension(R.styleable.NewImageView_borderWidth, 0f)
            borderColor = getColor(R.styleable.NewImageView_borderColor, Color.TRANSPARENT)
            borderCorner = getDimension(R.styleable.NewImageView_borderCorner, 0f)
            recycle()
        }
    }

    private fun initPaint() {
        linePaint.apply {
            strokeWidth = borderWidth
            color = borderColor
        }
    }

    private fun initPath() {
        if (border == Type.CIRCLE)
            pathCircle.addCircle(viewWidth / 2, viewHeight / 2, radius, Path.Direction.CW)
        else {
            val array = FloatArray(8)
            repeat(8) {
                array[it] = borderCorner
            }
            pathOfRadius.addRoundRect(
                RectF(0f, 0f, radius * 2, radius * 2),
                array,
                Path.Direction.CW
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        viewHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        viewWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        radius = min(viewHeight, viewWidth) / 2

        Log.d(TAG, "onMeasure: viewHeight:$viewHeight")
        Log.d(TAG, "onMeasure: viewWidth:$viewWidth")
        Log.d(TAG, "onMeasure: radius:$radius")
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
        initPaint()
        initPath()
    }

    private fun drawCircleImage(canvas: Canvas?) {
        canvas?.let {
            Log.d(TAG, "drawCircleImage: here")
            it.clipPath(pathCircle)
            it.drawPath(pathCircle, linePaint)
        }
    }

    private fun drawRoundCircle(canvas: Canvas?) {
        canvas?.let {
            it.clipPath(pathOfRadius)
            it.drawPath(pathOfRadius, linePaint)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d(TAG, "onDraw: image is not null and canvas = null? " + (canvas == null))
        if (border == Type.CIRCLE)
            drawCircleImage(canvas)
        else
            drawRoundCircle(canvas)
        super.onDraw(canvas)
    }

}