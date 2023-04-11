package com.example.storyappsubmission.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyappsubmission.R

class CustomEditText:AppCompatEditText, View.OnTouchListener {
    private lateinit var clearButtomImage : Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    private fun showClearButton(){
        setButtonDrawables()
    }

    private fun hideClearButton(){
        setButtonDrawables()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
    private fun init(){
        clearButtomImage = ContextCompat.getDrawable(context, R.drawable.clear_24) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null){
            val clearButtonStart : Float
            val clearButtonEnd : Float
            var isClearButtonClicked =false

            if(layoutDirection == View.LAYOUT_DIRECTION_RTL){
                clearButtonEnd = (clearButtomImage.intrinsicWidth + paddingStart).toFloat()
                when{
                    event!!.x < clearButtonEnd -> isClearButtonClicked = true
                }
            }else{
                clearButtonStart = (width - paddingEnd - clearButtomImage.intrinsicWidth).toFloat()
                when {
                    event!!.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButtomImage = ContextCompat.getDrawable(context, R.drawable.clear_24) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButtomImage = ContextCompat.getDrawable(context, R.drawable.clear_24) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
            return false
        }
    }
