package mobdao.com.openquiz.uicomponents.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.media.SoundPool
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import mobdao.com.openquiz.uicomponents.R

class CountdownTimerView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    // region draw

    private val backgroundRect: RectF by lazy { RectF() }
    private val backgroundPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val numberPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var defaultBackgroundColor = ContextCompat.getColor(context, R.color.white)
    private var cornerRadius: Float = 0f

    // endregion

    // region animation

    private val bounceAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.cycle_bounce)
    }

    // endregion

    // region sound effects

    private var soundPool: SoundPool? = null
    private var countdownSoundId: Int? = null
    private var buzzSoundId: Int? = null

    // endregion

    private var countDownTimer: CountDownTimer? = null
    private var isExecuting = false
    private var initialTime = -1
    private var currentTime = -1
    
    var countdownFinishedCallback: (() -> Unit)? = null

    init {
        var textColor = Color.WHITE
        var textSize = (12f * resources.displayMetrics.scaledDensity)

        attrs?.let { context.obtainStyledAttributes(it, R.styleable.CountdownTimerView) }?.run {
            cornerRadius =
                getDimension(R.styleable.CountdownTimerView_cdt_cornerRadius, cornerRadius)
            defaultBackgroundColor =
                getColor(R.styleable.CountdownTimerView_cdt_backgroundColor, defaultBackgroundColor)
            initialTime = getInt(R.styleable.CountdownTimerView_cdt_initialTime, initialTime)
            textColor = getColor(R.styleable.CountdownTimerView_android_textColor, textColor)
            textSize = getDimension(R.styleable.CountdownTimerView_android_textSize, textSize)

            recycle()
        }

        backgroundPaint.color = defaultBackgroundColor
        numberPaint.color = textColor
        numberPaint.textSize = textSize
        currentTime = initialTime
        if (!isInEditMode) {
            soundPool = SoundPool.Builder().setMaxStreams(6).build()
            countdownSoundId = soundPool?.load(context, R.raw.sound_countdown, 0)
            buzzSoundId = soundPool?.load(context, R.raw.sound_buzz, 1)
        }
    }

    /*
    A view is drawn in a canvas. onMeasure() is used to define the size of this canvas. This function
    is not required to be overridden but if we don't, canvas size will be as large as possible, which
    can take entire layout size.
    It is here where wrap_content, match_parent and specific size are used and handled.
    So in a few words, onMeasure() uses wrap_content, match_parent and specific size to define canvas size
    that will be used in onDraw()
    */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fontMetrics = numberPaint.fontMetrics

        // Measure maximum possible width of text
        val maxTextWidth = numberPaint.measureText(initialTime.toString())

        // Estimate maximum possible height of text
        val maxTextHeight = -fontMetrics.top + fontMetrics.bottom

        // Add padding to maximum width calculation
        val desiredWidth = (maxTextWidth + paddingLeft + paddingRight).toInt()

        // Add padding to maximum height calculation
        val desiredHeight = (maxTextHeight + paddingTop + paddingBottom).toInt()

        // Reconcile size that this view wants to be with the size the parent will let it be.
        val measureWidth: Int = resolveSize(desiredWidth, widthMeasureSpec)
        val measuredHeight: Int = resolveSize(desiredHeight, heightMeasureSpec)

        // Store the final measured dimensions
        // App throws IllegalStateException if this is not called
        setMeasuredDimension(measureWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val canvasWidth = width.toFloat()
        val canvasHeight = height.toFloat()
        val displayedCount = currentTime.toString()

        // Draw the background

        backgroundRect.set(0f, 0f, canvasWidth, canvasHeight)
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint)

        // Draw the text

        // Measure the width of text to display
        val centerX = canvasWidth * 0.5f
        val textWidth = numberPaint.measureText(displayedCount)

        // Figure out an x-coordinate that will center the text in the canvas
        val textX = (centerX - textWidth * 0.5f).toInt().toFloat()
        // Figure out an y-coordinate that will center the text in the canvas
        val textY = (canvasHeight / 2 - (numberPaint.descent() + numberPaint.ascent()) / 2)
        // Draw
        canvas.drawText(displayedCount, textX, textY, numberPaint)
    }

    fun start() {
        if (isExecuting) return
        isExecuting = true
        countDownTimer = object : CountDownTimer(currentTime * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished < (currentTime - 1) * 1000) // ignore first tick
                    decrementTime()
            }

            override fun onFinish() {
                decrementTime()
                countdownFinishedCallback?.invoke()
            }
        }.start()
    }

    fun pause() {
        countDownTimer?.cancel()
    }

    // region private

    private fun decrementTime() {
        currentTime--

        backgroundPaint.color = when (currentTime) {
            0 -> ContextCompat.getColor(context, R.color.red)
            1 -> ContextCompat.getColor(context, R.color.red1)
            2 -> ContextCompat.getColor(context, R.color.red2)
            3 -> ContextCompat.getColor(context, R.color.red3)
            4 -> ContextCompat.getColor(context, R.color.red4)
            else -> defaultBackgroundColor
        }

        if (currentTime < 5) {
            if (currentTime > 0) {
                countdownSoundId?.let { soundPool?.play(it, 1f, 1f, 1, 0, 1f) }
            } else {
                buzzSoundId?.let { soundPool?.play(it, 1f, 1f, 2, 0, 1f) }
            }
            startAnimation(bounceAnimation)
        }
        invalidate()
    }

    // endregion
}