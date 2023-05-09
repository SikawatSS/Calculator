package com.example.testgpt


import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private var inputtext : TextView? = null
    private var ButtonPlus : Button? = null
    private var ButtonDel : Button? = null
    private var ButtonDivine : Button? = null
    private var ButtonMulti : Button? = null
    private lateinit var gestureDetector: GestureDetectorCompat
    var lastnumber : Boolean = false
    var lastdot : Boolean = false
    var bgcolormode : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculatorconst)
        supportActionBar?.hide()
        inputtext = findViewById<TextView>(R.id.inputtext)
        ButtonPlus = findViewById(R.id.ButtonPlus)
        ButtonDel = findViewById(R.id.ButtonDel)
        ButtonDivine = findViewById(R.id.ButtonDivine)
        ButtonMulti = findViewById(R.id.ButtonMulti)

//        var ac = findViewById<Button>(R.id.ButtonAc)
//        ac.setOnClickListener {
//            var plus = findViewById<Button>(R.id.ButtonPlus)
//            plus.visibility = View.INVISIBLE
//        }

        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                // Handle the fling gesture
                val deltaX = e2.x - e1.x
                val deltaY = e2.y - e1.y
                if (abs(deltaX) > abs(deltaY)) {
                    // Horizontal swipe detected
                    if (deltaX > 0 ) {
                        RemoveComma()
                        var input = inputtext?.text.toString()
                        var length = input.length
                        if (input.length != 0){
                            inputtext?.text = input.substring(0,input.length-1)
                        }
                    } else {
                        // Swipe left detected

                        var input = inputtext?.text.toString()
                        var length = input.length
                        if (input.length != 0){
                            inputtext?.text = input.substring(0,input.length-1)
                        }
                        //inputtext?.text = input.substring(0,input.length-1)

                    }
                } else {
                    // Vertical swipe detected
                    if (deltaY > 0) {
                        // Swipe down detected
                    } else {
                        // Swipe up detected
                    }
                }
                return true
            }
        })
    }
    fun Resize(){
        var text = inputtext?.text.toString()
        var length = text.length
        Log.e("length",length.toString())
        if (length == 6){
            inputtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50f)
        }
        if (length == 9){
            inputtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40f)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun Onnumber(view: View){
        val zero = inputtext?.text.toString()
        if (zero == "0"){
            inputtext?.text = ""
        }
        inputtext?.append((view as Button).text)
        
        inputtext?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do something while the text is being changed
                var position = inputtext?.text.toString().length -1
                if (s != null) {
                    var length = s.length
                    var check = true
                    if (length == 4 && check ){
                        inputtext?.setText(StringBuilder(s).insert(s.length - 3,",").toString())
                    }
                    if (s.length == 8){
                        inputtext?.setText(StringBuilder(s).insert(s.length - 3,",").toString())

                    }
//                    if (length == 5 && check){
//                        inputtext?.setText(StringBuilder(s).insert(s.length - 4,",").toString())
//                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do something after the text has been changed
            }
        })
            //ลองใช้อันนี้ดูพรุ้งนี้
//        val text = textView.text.toString()
//
//        if (text.endsWith(",")) {
//            textView.text = text.substring(0, text.length - 1)
//        }
        lastnumber = true
        lastdot = false
        Resize()
    }

    fun Onclear(view: View){
        inputtext?.text = "0"
        inputtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        ChangeButtonColor()


    }

    fun Ondot (view: View){
        if (lastnumber && !lastdot){
        inputtext?.append(".")
            lastnumber = false
            lastdot = true
        }
    }

    fun Onpercent(view: View){
        var input = inputtext?.text.toString().toDouble()
        inputtext?.text = removedot((input / 100).toString())
    }

    fun OnDelbeforenumber(view: View){
        inputtext?.setText("-")
    }

    fun OnOperator (view: View){

        inputtext?.text.let {
            if (lastnumber && !checkOperator(it.toString())){
                inputtext?.append((view as Button).text)
                lastnumber = false
                lastdot = false
                if (!bgcolormode){
                    view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BABABA"))
                    bgcolormode = true
                   }
                }
        }
    }
    fun ChangeButtonColor(){
        ButtonPlus?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        ButtonDel?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        ButtonMulti?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        ButtonDivine?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        bgcolormode = false
    }
    fun RemoveComma(){
        val text = inputtext?.text.toString()
        val textwithoutcomma = text.replace(",","")
        inputtext?.text = textwithoutcomma
        Log.e("RemoveComma",textwithoutcomma)
    }
    fun onEqual(view: View){
        if (lastnumber ){
            ChangeButtonColor()
            RemoveComma()
            var tvValue = inputtext?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    var  splitValue = tvValue.split("-")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    inputtext?.text  = removedot((leftside.toDouble() - rightside.toDouble()).toString())
                }
                if (tvValue.contains("+")){
                    var  splitValue = tvValue.split("+")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    //inputtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
                    inputtext?.text  = removedot((leftside.toDouble() + rightside.toDouble()).toString())
                }
                if (tvValue.contains("x")){
                    var  splitValue = tvValue.split("x")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    inputtext?.text  = removedot((leftside.toDouble() * rightside.toDouble()).toString())
                }
                if (tvValue.contains("÷")){
                    var  splitValue = tvValue.split("÷")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    inputtext?.text  = removedot((leftside.toDouble() / rightside.toDouble()).toString())
                }

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun removedot(string :String): String {
        var value = string
        if (string.contains(".0"))
            value = string.substring(0,string.length-2)
        return  value
    }

    fun checkOperator(value : String) : Boolean {
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}
