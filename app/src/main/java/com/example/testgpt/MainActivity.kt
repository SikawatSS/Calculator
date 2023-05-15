package com.example.testgpt


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import java.text.DecimalFormat
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private var inputAndResultTextView : TextView? = null
    private var secondTextView : TextView? = null
    private var thirdTextView : TextView? = null
    private var fourTextView : TextView? = null
    private var buttonPlus : Button? = null
    private var buttonMinus : Button? = null
    private var buttonDivine : Button? = null
    private var buttonMulti : Button? = null
    private var buttonClear : Button?= null
    private lateinit var gestureDetector: GestureDetectorCompat
    var lastNumber : Boolean = false
    var lastDot : Boolean = false
    var checkCanUseDot : Boolean = false
    var bgColorMode : Boolean = false
    var onClear : Boolean = false



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculatorconst)
        supportActionBar?.hide()
        inputAndResultTextView = findViewById<TextView>(R.id.inputtext)
        secondTextView = findViewById<TextView>(R.id.secondtext)
        thirdTextView = findViewById<TextView>(R.id.threetext)
        fourTextView = findViewById<TextView>(R.id.fourtext)
        buttonPlus = findViewById(R.id.ButtonPlus)
        buttonMinus = findViewById(R.id.ButtonDel)
        buttonDivine = findViewById(R.id.ButtonDivine)
        buttonMulti = findViewById(R.id.ButtonMulti)
        buttonClear = findViewById(R.id.ButtonAc)
        inputAndResultTextView?.visibility = View.VISIBLE
        secondTextView?.visibility = View.INVISIBLE
        thirdTextView?.visibility = View.INVISIBLE
        fourTextView?.visibility = View.INVISIBLE





        inputAndResultTextView?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val decimalFormat = DecimalFormat("#,###")
                var tvValue = inputAndResultTextView?.text.toString()
                var numberS = s.toString()
                if (inputAndResultTextView?.text.toString().isNotEmpty() && !inputAndResultTextView?.text.toString().startsWith(".")
                    && !inputAndResultTextView?.text.toString().startsWith(",")
                    && !tvValue.contains("+")  && !tvValue.contains("-")
                    && !tvValue.contains("x") && !tvValue.contains("÷")
                    && !tvValue.contains(".") && !tvValue.contains("e") ) {
                    val number = s.toString().replace(",", "").toDouble()
                    val formattedString =
                        if (number > 999999999 && number.toString().length >= 12) {
                         "999,999,999"
                        } else {
                        decimalFormat.format(number)
                    }
                    inputAndResultTextView?.removeTextChangedListener(this)
                    inputAndResultTextView?.setText(formattedString)
                    inputAndResultTextView?.addTextChangedListener(this)
                    Log.e("beforeplus",inputAndResultTextView?.text.toString())

                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        secondTextView?.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val decimalFormat = DecimalFormat("#,###")
                var tvValue = secondTextView?.text.toString()
                if (secondTextView?.text.toString().isNotEmpty() && !secondTextView?.text.toString().startsWith(".")
                    && !secondTextView?.text.toString().startsWith(",")
                    && !tvValue.contains("+")  && !tvValue.contains("-")
                    && !tvValue.contains("x") && !tvValue.contains("÷")
                    && !tvValue.contains(".")) {
                    val number = s.toString().replace(",", "").toDouble()
                    val formattedString = if (number > 999999999 && number.toString().length >= 12) {
                        "999,999,999"

                    } else {
                        decimalFormat.format(number)
                    }
                    secondTextView?.removeTextChangedListener(this)
                    secondTextView?.setText(formattedString)
                    secondTextView?.addTextChangedListener(this)
                    //Log.e("afterplus",secondtext?.text.toString())
                }

            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        thirdTextView?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val decimalFormat = DecimalFormat("#,###")
                var tvValue = thirdTextView?.text.toString()
                if (thirdTextView?.text.toString().isNotEmpty() && !thirdTextView?.text.toString().startsWith(".")
                    && !thirdTextView?.text.toString().startsWith(",")
                    && !tvValue.contains("+")  && !tvValue.contains("-")
                    && !tvValue.contains("x") && !tvValue.contains("÷")
                    && !tvValue.contains(".")) {
                    val number = s.toString().replace(",", "").toDouble()
                    val formattedString = if (number > 999999999 && number.toString().length >= 12) {
                        "999,999,999"
                    } else {
                        decimalFormat.format(number)
                    }
                    thirdTextView?.removeTextChangedListener(this)
                    thirdTextView?.setText(formattedString)
                    thirdTextView?.addTextChangedListener(this)
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
                        removeComma()
                        var input = inputAndResultTextView?.text.toString()
                        var length = input.length
                        if (input.length != 0){
                            inputAndResultTextView?.text = input.substring(0,input.length-1)
                            var text = inputAndResultTextView?.text.toString()
                            if (text.contains("+") || text.contains("-")
                                || text.contains("x") || text.contains("÷")){
                                val parts = text.split("+","-","x","÷")
                                if (parts.size == 2) {
                                    val secondNumber = parts[1].trim()
                                    if (secondNumber.isEmpty()){
                                        inputAndResultTextView?.append("0")
                                    }
                                    thirdTextView?.text = secondNumber
                                    thirdTextView?.visibility = View.VISIBLE
                                }
                            }


                        }
                    } else {
                        // Swipe left detected
                        removeComma()
                        var input = inputAndResultTextView?.text.toString()
                        var length = input.length
                        if (input.length != 0){
                            inputAndResultTextView?.setText(input.substring(0,input.length-1))
                            var text = inputAndResultTextView?.text.toString()
                            if (text.contains("+") || text.contains("-")
                                || text.contains("x") || text.contains("÷")){
                                val parts = text.split("+","-","x","÷")
                                if (parts.size == 2) {
                                    val secondNumber = parts[1].trim()
                                    if (secondNumber.isEmpty()){
                                        inputAndResultTextView?.append("0")
                                    }
                                    thirdTextView?.text = secondNumber
                                    thirdTextView?.visibility = View.VISIBLE
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
    fun changeTextSize(){
        var text = inputAndResultTextView?.text.toString()
        var length = text.length
        Log.e("length",length.toString())
        if (length == 6){
            inputAndResultTextView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50f)
            secondTextView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50f)
            thirdTextView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50f)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun numberOnclick(view: View){
        val zero = inputAndResultTextView?.text.toString()
        if (zero == "0"){
            inputAndResultTextView?.text = ""
            secondTextView?.text = ""
        }
            if (!inputAndResultTextView?.text.toString().isEmpty()) {
            buttonClear?.text = "C"
        }
        lastNumber = true
        lastDot = false
        inputAndResultTextView?.append((view as Button).text)
        changeTextSize()
        if (zero.contains("+") || zero.contains("-")
            || zero.contains("x") || zero.contains("÷")){
            thirdTextView?.append((view as Button).text)
            inputAndResultTextView?.visibility = View.INVISIBLE
            secondTextView?.visibility = View.INVISIBLE
            thirdTextView?.visibility = View.VISIBLE
        }
    }

    fun onClear(view: View){
        inputAndResultTextView?.text = "0"
        inputAndResultTextView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        secondTextView?.text = "0"
        secondTextView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        thirdTextView?.text = "0"
        thirdTextView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        fourTextView?.text = "0"
        fourTextView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        inputAndResultTextView?.visibility = View.VISIBLE
        secondTextView?.visibility = View.INVISIBLE
        thirdTextView?.visibility = View.INVISIBLE
        fourTextView?.visibility = View.INVISIBLE
        lastNumber = false
        lastDot = false
        buttonClear?.text = "AC"
        changeButtonColor()



    }

    fun Ondot (view: View){
        if (lastNumber && !lastDot){
        inputAndResultTextView?.append(".")
            thirdTextView?.append(".")
            lastNumber = false
            lastDot = true
        }
    }

    fun changeValuetoPercent(view: View){
        var input = inputAndResultTextView?.text.toString()
        val number = input.toString().replace(",", "").toDouble()
        inputAndResultTextView?.text = removeDot((number / 100).toString())
    }

    fun addMinusBeforeNumber(view: View){
        var text = inputAndResultTextView?.text.toString()
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
                inputAndResultTextView?.text = formattedString
            }
            else{
                inputAndResultTextView?.text = newtext.toString()
            }
        }else{
            var newtext = 0 * -1
            inputAndResultTextView?.text = newtext.toString()
        }
    }

    fun operatorOnclick (view: View){
        secondTextView?.setText("")
        thirdTextView?.setText("")
        inputAndResultTextView?.text.let {
            if (lastNumber && !checkOperator(it.toString())){
                inputAndResultTextView?.append((view as Button).text)
                lastNumber = false
                lastDot = false
                if (!bgColorMode){
                    view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BABABA"))
                    bgColorMode = true
                   }
                secondTextView?.visibility = View.INVISIBLE
                inputAndResultTextView?.visibility = View.INVISIBLE
                thirdTextView?.visibility = View.INVISIBLE
                    var textbeforeoperation = inputAndResultTextView?.text.toString()
                if (textbeforeoperation.contains("+") ){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('+')
                    secondTextView?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.contains("-")){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('-')
                    secondTextView?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.contains("x")){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('x')
                    secondTextView?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.contains("÷")){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('÷')
                    secondTextView?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-") && textbeforeoperation.endsWith("+")){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('+')
                    secondTextView?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-")&& textbeforeoperation.endsWith("-")){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('-')
                    secondTextView?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-")&& textbeforeoperation.endsWith("x")){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('x')
                    secondTextView?.visibility = View.VISIBLE
                }
                if (textbeforeoperation.startsWith("-")&& textbeforeoperation.endsWith("÷")){
                    secondTextView?.text = textbeforeoperation.substringBeforeLast('÷')
                    secondTextView?.visibility = View.VISIBLE
                }
                var textbeforeoperationfrom3 = thirdTextView?.text.toString()
                if (textbeforeoperationfrom3.endsWith("x")){
                    secondTextView?.text = textbeforeoperationfrom3.substringBeforeLast('x')
                    secondTextView?.visibility = View.VISIBLE
                }
                checkCanUseDot = true
                }
        }
    }
    fun changeButtonColor(){
        buttonPlus?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        buttonMinus?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        buttonMulti?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        buttonDivine?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff8000"))
        bgColorMode = false
    }
    fun removeComma(){
        val text = inputAndResultTextView?.text.toString()
        val textwithoutcomma = text.replace(",","")
        inputAndResultTextView?.text = textwithoutcomma
    }
    fun onEqual(view: View){
        secondTextView?.setText("")
        thirdTextView?.setText("")
        thirdTextView?.visibility = View.INVISIBLE
        secondTextView?.visibility = View.INVISIBLE
        inputAndResultTextView?.visibility = View.VISIBLE
        if (lastNumber ){
            changeButtonColor()
            removeComma()
            var tvValue = inputAndResultTextView?.text.toString()
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
                    if (rightside.length >= 9) {
                        var newrightside = rightside.substring(0, 9)
                        var result = ((leftside.toDouble() - newrightside.toDouble()).toString())
                        inputAndResultTextView?.text = (deleteDotZero(result))
                        secondTextView?.setText("")
                        thirdTextView?.setText("")
                        Log.e("from-",inputAndResultTextView?.text.toString())
                    }else{
                        var result = ((leftside.toDouble() - rightside.toDouble()).toString())
                        inputAndResultTextView?.text = (deleteDotZero(result))
                    }
//                    if (rightside.toDouble() > 9999999999){
//                        inputtext?.setText("0")
//                        Log.e("from-",inputtext?.text.toString())
//                    }
                    //

//                        var result = ((leftside.toDouble() - rightside.toDouble()).toString())
//                        inputtext?.text = (deletedotzero(result))
//                        secondtext?.setText("")
//                        threetext?.setText("")
//                        Log.e("from-",inputtext?.text.toString())


                   // }

                }
                if (tvValue.contains("+")){
                    var  splitValue = tvValue.split("+")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    if (rightside.length >= 9){
                        var newrightside = rightside.substring(0,9)
                        Log.e("newrigthside",newrightside)
                        var result = ((leftside.toDouble() + newrightside.toDouble()).toString())
                        var number = (leftside.toDouble() + newrightside.toDouble())
                        if (result.toDouble() > 999999999){
                            inputAndResultTextView?.text = ((result)).toString()
                            Log.e("from+if",inputAndResultTextView?.text.toString())
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                            val numberInScientificNotation = String.format("%.0e", number)
                            Log.e("Scinumber",numberInScientificNotation)
                            val numberAsString = numberInScientificNotation.toString().replace("+0", "")
                            inputAndResultTextView?.text = ((numberInScientificNotation)).toString()
                            val resultList = mutableListOf<String>()
                            numberInScientificNotation.split("+").forEach {
                                resultList.add(it.replace("+", "").replace("0", ""))
                            }
                            var result0 = resultList[0].toString()
                            var result1 = resultList[1].toString()
                            inputAndResultTextView?.text = "$result0$result1"
                        }
                        else{
                            inputAndResultTextView?.text = (deleteDotZero(result))
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                            Log.e("from+else",inputAndResultTextView?.text.toString())
                            Log.e("resultfour",fourTextView?.text.toString())
                        }
                    }
                    else {
                        var newrightside = rightside.substring(0,rightside.length)
                        Log.e("newrigthside",newrightside)
                        var result = ((leftside.toDouble() + newrightside.toDouble()).toString())
                        if (result.toDouble() > 999999999){
                            inputAndResultTextView?.text = ((result)).toString()
                            Log.e("from+if",inputAndResultTextView?.text.toString())
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                        }
                        else{
                            inputAndResultTextView?.text = (deleteDotZero(result))
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                            Log.e("from+else",inputAndResultTextView?.text.toString())
                            Log.e("resultfour",fourTextView?.text.toString())
                        }
                    }
                }
                if (tvValue.contains("x")){
                    var  splitValue = tvValue.split("x")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    if (rightside.length >= 9){
                        var newrightside = rightside.substring(0,9)
                        Log.e("newrigthside",newrightside)
                        var result = ((leftside.toDouble() * newrightside.toDouble()).toString())
                        var number = (leftside.toDouble() * newrightside.toDouble())
                        if (result.toDouble() > 999999999){
                            inputAndResultTextView?.text = ((result)).toString()
                            Log.e("from+if",inputAndResultTextView?.text.toString())
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                            val numberInScientificNotation = String.format("%.0e", number)
                            Log.e("Scinumber",numberInScientificNotation)
                            //inputtext?.text = ((numberInScientificNotation)).toString()
                            val resultList = mutableListOf<String>()
                            numberInScientificNotation.split("x").forEach {
                                resultList.add(it.replace("x", "").replace("0", ""))
                            }
                            var result0 = resultList[0].toString()
                           // var result1 = resultList[1].toString()
                            inputAndResultTextView?.text = "$result0"
                        }
                        else{
                            inputAndResultTextView?.text = (deleteDotZero(result))
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                            Log.e("from+else",inputAndResultTextView?.text.toString())
                            Log.e("resultfour",fourTextView?.text.toString())
                        }
                    }
                    else {
                        var newrightside = rightside.substring(0,rightside.length)
                        Log.e("newrigthside",newrightside)
                        var result = ((leftside.toDouble() * newrightside.toDouble()).toString())
                        if (result.toDouble() > 999999999){
                            inputAndResultTextView?.text = ((result)).toString()
                            Log.e("from+if",inputAndResultTextView?.text.toString())
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                        }
                        else{
                            inputAndResultTextView?.text = (deleteDotZero(result))
                            secondTextView?.setText("")
                            thirdTextView?.setText("")
                            Log.e("from+else",inputAndResultTextView?.text.toString())
                            Log.e("resultfour",fourTextView?.text.toString())
                        }
                    }
//                    var  splitValue = tvValue.split("x")
//                    var leftside = splitValue[0]
//                    var rightside = splitValue[1]
//                    if (prefix.isNotEmpty()){
//                        leftside = prefix + leftside
//                    }
//                    var result = ((leftside.toDouble() * rightside.toDouble()).toString())
//                    inputtext?.text = (deletedotzero(result))
//                    secondtext?.setText("")
//                    threetext?.setText("")
//                    Log.e("fromx",inputtext?.text.toString())

                }
                if (tvValue.contains("÷")){
                    var  splitValue = tvValue.split("÷")
                    var leftside = splitValue[0]
                    var rightside = splitValue[1]
                    if (prefix.isNotEmpty()){
                        leftside = prefix + leftside
                    }
                    var result = ((leftside.toDouble() / rightside.toDouble()).toString())
                    inputAndResultTextView?.text = (deleteDotZero(result))
                    Log.e("from/",inputAndResultTextView?.text.toString())
                    secondTextView?.setText("")
                    thirdTextView?.setText("")

                }
                secondTextView?.visibility = View.INVISIBLE
                thirdTextView?.visibility = View.INVISIBLE
                inputAndResultTextView?.visibility = View.VISIBLE

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun deleteDotZero(s : String) : String {
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

    fun removeDot(string :String): String {
        var value = string
        var length = inputAndResultTextView?.length()
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
