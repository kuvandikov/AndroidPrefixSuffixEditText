package io.github.kuvandikov.prefixsuffix

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText

/**
 * [AppCompatEditText] with easy prefix and suffix support.
 *
 * Inspired by https://gist.github.com/morristech/5480419
 */
class PrefixSuffixEditText constructor(
    context: Context,
    attrs: AttributeSet
) : AppCompatEditText(context, attrs) {

    private val textPaint: TextPaint by lazy {
        TextPaint().apply {
            color = currentHintTextColor
            textAlign = Paint.Align.LEFT
            isAntiAlias = true
            this.typeface = typeface
        }
    }

    private val prefixDrawable: PrefixDrawable by lazy { PrefixDrawable(paint) }

    var prefix: String = ""
        set(value) {
            if (value.isNotBlank()) {
                Log.v(TAG, "prefix: $value")
            }
            field = value
            prefixDrawable.text = value
            updatePrefixDrawable()
        }

    var suffix: String? = null
        set(value) {
            field = if (!value.isNullOrBlank()) {
                Log.v(TAG, "suffix: $value")
                " ".plus(value)
            }else{
                value
            }
            invalidate()
        }

    // These are used to store details obtained from the EditText's rendering process
    private val firstLineBounds = Rect()

    private var isInitialized = false

    init {
        textPaint.textSize = textSize

        updatePrefixDrawable()
        isInitialized = true

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.PrefixSuffixEditText)
        prefix = typedArray.getString(R.styleable.PrefixSuffixEditText_prefix) ?: ""
        suffix = typedArray.getString(R.styleable.PrefixSuffixEditText_suffix)
        typedArray.recycle()
    }

    override fun setTypeface(typeface: Typeface?) {
        super.setTypeface(typeface)

        if (isInitialized) {
            // this is first called from the constructor when it's not initialized, yet
            textPaint.typeface = typeface
        }

        postInvalidate()
    }

    public override fun onDraw(c: Canvas) {
        textPaint.color = currentHintTextColor
        textPaint.typeface = typeface
        textPaint.textSize = textSize

        val lineBounds = getLineBounds(0, firstLineBounds)
        prefixDrawable.let {
            it.lineBounds = lineBounds
            it.paint = textPaint
        }

        super.onDraw(c)

        // Now we can calculate what we need!
        val text = text.toString()
        val prefixText: String = prefixDrawable.text
        val textWidth: Float = if (text.isNotEmpty()) {
            textPaint.measureText(prefixText + text) + paddingLeft
        } else {
            textPaint.measureText(prefixText + hint) + paddingLeft
            return
        }

        suffix?.let {
            // We need to draw this like this because
            // setting a right drawable doesn't work properly and we want this
            // just after the text we are editing (but untouchable)
            val baselineY = baseline.toFloat()
            c.drawText(it, textWidth, baselineY, textPaint)
        }
    }

    private fun updatePrefixDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelative(prefixDrawable, null, null, null)
        } else {
            setCompoundDrawables(prefixDrawable, null, null, null)
        }
    }

    companion object {
        private const val TAG = "PrefixSuffixEditText"
    }
}