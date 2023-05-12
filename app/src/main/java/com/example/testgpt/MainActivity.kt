package com.example.testgpt


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.abs
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private var inputtext : TextView? = null
    private var secondtext : TextView? = null
    private var threetext : TextView? = null
    private var fourtext : TextView? = null
    private var ButtonPlus : Button? = null
    private var ButtonDel : Button? = null
    private var ButtonDivine : Button? = null
    private var ButtonMulti : Button? = null
    private lateinit var gestureDetector: GestureDetectorCompat
    var lastnumber : Boolean = false
    var lastdot : Boolean = false
    var candot : Boolean = false
    var bgcolormode : Boolean = false




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculatorconst)
        supportActionBar?.hide()
        inputtext = findViewById<TextView>(R.id.inputtext)
        secondtext = findViewById<TextView>(R.id.secondtext)
        threetext = findViewById<TextView>(R.id.threetext)
        fourtext = findViewById<TextView>(R.id.fourtext)
        ButtonPlus = findViewById(R.id.ButtonPlus)
        ButtonDel = findViewById(R.id.ButtonDel)
        ButtonDivine = findViewById(R.id.ButtonDivine)
        ButtonMulti = findViewById(R.id.ButtonMulti)
        inputtext?.visibility = View.VISIBLE
        secondtext?.visibility = View.INVISIBLE
        threetext?.visibility = View.INVISIBLE
        fourtext?.visibility = View.INVISIBLE



        inputtext?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val decimalFormat = DecimalFormat("#,###")
                var tvValue = inputtext?.text.toString()
                var numberS = s.toString()
                if (inputtext?.text.toString().isNotEmpty() && !inputtext?.text.toString().startsWith(".")
                    && !inputtext?.text.toString().startsWith(",")
                    && !tvValue.contains("+")  && !tvValue.contains("-")
                    && !tvValue.contains("x") && !tvValue.contains("÷")
                    && !tvValue.contains(".") ) {
                    val number = s.toString().replace(",", "").toDouble()
                    val formattedString =
                        if (number > 999999999 && number.toString().length >= 12) {
                         "999,999,999"
                        } else {
                        decimalFormat.format(number)
                    }
                    inputtext?.removeTextChangedListener(this)
                    inputtext?.setText(formattedString)
                    inputtext?.addTextChangedListener(this)
                    Log.e("beforeplus",inputtext?.text.toString())

                }
//                else if (numberS){
//
//                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        secondtext?.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val decimalFormat = DecimalFormat("#,###")
                var tvValue = secondtext?.text.toString()
                if (secondtext?.text.toString().isNotEmpty() && !secondtext?.text.toString().startsWith(".")
                    && !secondtext?.text.toString().startsWith(",")
                    && !tvValue.contains("+")  && !tvValue.contains("-")
                    && !tvValue.contains("x") && !tvValue.contains("÷")
                    && !tvValue.contains(".")) {
                    val number = s.toString().replace(",", "").toDouble()
                    val formattedString = if (number > 999999999 && number.toString().length >= 12) {
                        "999,999,999"

                    } else {
                        decimalFormat.format(number)
                    }
                    secondtext?.removeTextChangedListener(this)
                    secondtext?.setText(formattedString)
                    secondtext?.addTextChangedListener(this)
                    //Log.e("afterplus",secondtext?.text.toString())
                }

            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        threetext?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val decimalFormat = DecimalFormat("#,###")
                var tvValue = threetext?.text.toString()
                if (threetext?.text.toString().isNotEmpty() && !threetext?.text.toString().startsWith(".")
                    && !threetext?.text.toString().startsWith(",")
                    && !tvValue.contains("+")  && !tvValue.contains("-")
                    && !tvValue.contains("x") && !tvValue.contains("÷")
                    && !tvValue.contains(".")) {
                    val number = s.toString().replace(",", "").toDouble()
                    val formattedString = if (number > 999999999 && number.toString().length >= 12) {
                        "999,999,999"
                    } else {
                        decimalFormat.format(number)
                    }
                    threetext?.removeTextChangedListener(this)
                    threetext?.setText(formattedString)
                    threetext?.addTextChangedListener(this)
                    //Log.e("afterplus",threetext?.text.toString())
//                    val number = s.toString().replace(",", "").toDouble()
//                    val formattedString = decimalFormat.format(number)
//                    threetext?.removeTextChangedListener(this)
//                    threetext?.setText(formattedString)
//                    threetext?.addTextChangedListener(this)
//                    if (formattedString.length >= 12){
//                        threetext?.removeTextChangedListener(this)
//                        threetext?.setText(formattedString.substring(0,12))
//                        Log.e("inputtext",threetext?.text.toString())
//                        threetext?.addTextChangedListener(this)
//                    }
                }

            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
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
                            var text = inputtext?.text.toString()
                            if (text.contains("+") || text.contains("-")
                                || text.contains("x") || text.contains("÷")){
                                val parts = text.split("+","-","x","÷")
                                if (parts.size == 2) {
                                    val secondNumber = parts[1].trim()
                                    if (secondNumber.isEmpty()){
                                        inputtext?.append("0")
                                    }
                                    threetext?.text = secondNumber
                                    threetext?.visibility = View.VISIBLE
                                }
                            }


                        }
                    } else {
                        // Swipe left detected
                        RemoveComma()
                        var input = inputtext?.text.toString()
                        var length = input.length
                        if (input.length != 0){
                            inputtext?.setText(input.substring(0,input.length-1))
                            var text = inputtext?.text.toString()
                            if (text.contains("+") || text.contains("-")
                                || text.contains("x") || text.contains("÷")){
                                val parts = text.split("+","-","x","÷")
                                if (parts.size == 2) {
                                    val secondNumber = parts[1].trim()
                                    if (secondNumber.isEmpty()){
                                        inputtext?.append("0")
                                    }
                                    threetext?.text = secondNumber
                                    threetext?.visibility = View.VISIBLE
                                }
                            }
                        }
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
            secondtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50f)
            threetext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50f)
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
            secondtext?.text = ""
        }

        lastnumber = true
        lastdot = false
        inputtext?.append((view as Button).text)
        Resize()
        if (zero.contains("+") || zero.contains("-")
            || zero.contains("x") || zero.contains("÷")){
            threetext?.append((view as Button).text)
            inputtext?.visibility = View.INVISIBLE
            secondtext?.visibility = View.INVISIBLE
            threetext?.visibility = View.VISIBLE
        }
    }

    fun Onclear(view: View){
        inputtext?.text = "0"
        inputtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        secondtext?.text = "0"
        secondtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        threetext?.text = "0"
        threetext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        fourtext?.text = "0"
        fourtext?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        inputtext?.visibility = View.VISIBLE
        secondtext?.visibility = View.INVISIBLE
        threetext?.visibility = View.INVISIBLE
        fourtext?.visibility = View.INVISIBLE
        lastnumber = false
        lastdot = false
        ChangeButtonColor()
    }

    fun Ondot (view: View){
        if (lastnumber && !lastdot){
        inputtext?.append(".")
            threetext?.append(".")
            lastnumber = false
            lastdot = true
        }
    }

    fun Onpercent(view: View){
        var input = inputtext?.text.toString()
        val number = input.toString().replace(",", "").toDouble()
        inputtext?.text = removedot((number / 100).toString())
    }

    fun OnDelbeforenumber(view: View){
        var text = inputtext?.text.toString()
        val number = text.replace(",", "")
        if (number.toDouble() !== 0.0) {
            var newtext = number.toDouble() * -1
            var newtextstring = newtext.toString()
            Log.e("newtext",newtext.toString())
            Log.e("newtextstring",newtextstring.toString())
            if (newtextstring.contains(".0")){
                //inputtext?.text = newtextstring.substring(0,newtextstring.length - 2)
                var newcut = newtextstring.substring(0,newtextstring.length - 2)
                val decimalFormat = DecimalFormat("#,###")
                    val number = newcut.toString().replace(",", "").toDouble()
                    val formattedString = decimalFormat.format(number)
                inputtext?.text = formattedString
            }
            else{
                inputtext?.text = newtext.toString()
            }
        }else{
            var newtext = 0 * -1
            inputtext?.text = newtext.toString()
        }
    }

    fun OnOperator (view: View){
        secondtext?.setText("")
        threetext?.setText("")
        inputtext?.text.let {
            if (lastnumber && !checkOperator(it.toString())){
                inputtext?.append((view as Button).text)
                lastnumber = false
                lastdot = false
                if (!bgcolormode){
                    view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BABABA"))
                    bgcolormode = true
                   }
                secondtext?.visibility = View.INVISIBLE
                inputtext?.visibility = View.INVISIBLE
                threetext?.visibility = View.INVISIBLE
                    var textbeforeoperation = inputtext?.text.toString()
                if (textbeforeoperation.contains("+") ){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('+')
                    secondtext?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.contains("-")){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('-')
                    secondtext?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.contains("x")){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('x')
                    secondtext?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.contains("÷")){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('÷')
                    secondtext?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-") && textbeforeoperation.endsWith("+")){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('+')
                    secondtext?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-")&& textbeforeoperation.endsWith("-")){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('-')
                    secondtext?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-")&& textbeforeoperation.endsWith("x")){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('x')
                    secondtext?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-")&& textbeforeoperation.endsWith("÷")){
                    secondtext?.text = textbeforeoperation.substringBeforeLast('÷')
                    secondtext?.visibility = View.VISIBLE
                }
                var textbeforeoperationfrom3 = threetext?.text.toString()
                if (textbeforeoperationfrom3.endsWith("x")){
                    secondtext?.text = textbeforeoperationfrom3.substringBeforeLast('x')
                    secondtext?.visibility = View.VISIBLE
                }
                candot = true
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
    }
    fun onEqual(view: View){
        secondtext?.setText("")
        threetext?.setText("")
        threetext?.visibility = View.INVISIBLE
        secondtext?.visibility = View.INVISIBLE
        inputtext?.visibility = View.VISIBLE
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
                    if (rightside.toDouble() > 9999999999){
                        inputtext?.setText("0")
                    }else{
                        var result = ((leftside.toDouble() - rightside.toDouble()).toString())
                        inputtext?.text = (deletedotzero(result))
                        secondtext?.setText("")
                        threetext?.setText("")

                    }

                    //inputtext?.text  = removedot((leftside.toDouble() - rightside.toDouble()).toString())
                    //inputtext?.text  = ((leftside.toDouble() - rightside.toDouble()).toString())

//                    var result = ((leftside.toDouble() - rightside.toDouble()).toString())
//                    inputtext?.text = (deletedotzero(result))
//                    secondtext?.setText("")
//                    threetext?.setText("")

                }
                if (tvValue.contains("+")){
                    var  splitValue = tvValue.split("+")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    var result = ((leftside.toDouble() + rightside.toDouble()).toString())
                    if (result.toDouble() > 999999999){
                        //var intresult = result.toInt()
                        Log.e("Real result",result.toString())
                        inputtext?.text = ((result)).toString()
                        secondtext?.setText("")
                        threetext?.setText("")
                    }
                    else{
                    inputtext?.text = (deletedotzero(result))
                    secondtext?.setText("")
                    threetext?.setText("")
                    Log.e("result",inputtext?.text.toString())
                    Log.e("resultfour",fourtext?.text.toString())
                    }
//                    Log.e("Real result",result)
//                    inputtext?.text = (deletedotzero(result))
//                    fourtext?.text = (deletedotzero(result))
//                    secondtext?.setText("")
//                    threetext?.setText("")
//                    Log.e("result",inputtext?.text.toString())
//                    Log.e("resultfour",fourtext?.text.toString())

                }
                if (tvValue.contains("x")){
                    var  splitValue = tvValue.split("x")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    //inputtext?.text  = removedot((leftside.toDouble() * rightside.toDouble()).toString())
                    //inputtext?.text  = ((leftside.toDouble() * rightside.toDouble()).toString())
                    var result = ((leftside.toDouble() * rightside.toDouble()).toString())
                    inputtext?.text = (deletedotzero(result))
                    secondtext?.setText("")
                    threetext?.setText("")

                }
                if (tvValue.contains("÷")){
                    var  splitValue = tvValue.split("÷")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    //inputtext?.text  = removedot((leftside.toDouble() / rightside.toDouble()).toString())
                    //inputtext?.text  = ((leftside.toDouble() / rightside.toDouble()).toString())
                    var result = ((leftside.toDouble() / rightside.toDouble()).toString())
                    inputtext?.text = (deletedotzero(result))
                    secondtext?.setText("")
                    threetext?.setText("")

                }
                secondtext?.visibility = View.INVISIBLE
                threetext?.visibility = View.INVISIBLE
                inputtext?.visibility = View.VISIBLE

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    fun Double.toExponential(fractionDigits: Int): String {
        val exponential = String.format("%.${fractionDigits}e", this)
        val parts = exponential.split("e")
        val coefficient = parts[0]
        val exponent = parts[1].toInt()
        return "$coefficient E${exponent}"
    }

    fun deletedotzero(s : String) : String {
        var value = s
        val originalText = s // "23232.2423432432432432"
        val originalValue = originalText.toDouble() // 23232.2423432432432432
        val formattedValue = "%.3f".format(originalValue) // "23232.242"
        if (formattedValue.contains(".000")){
            var newvalue = formattedValue.substring(0 , formattedValue.length - 4)
            return  newvalue

        }
        return  formattedValue

    }

    fun removedot(string :String): String {
        var value = string
        var length = inputtext?.length()
        if (string.contains(".0")) {
            value = string.substring(0, string.length - 2)
        }
        return value
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
