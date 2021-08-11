package sg.cloudwind.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    override fun <T : View?> findViewById(id: Int): T {
        return super.findViewById(id)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric = true
    }
    fun onClear(view: View){
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }
    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false;
            lastDot = true;
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            // if last entered is numeric, then can do calculation
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    tvValue = tvValue.substring(1)
                    prefix = "-"

                }
                if(tvValue.contains("-")){
                    var substring = tvValue.split("-")
                    var one = substring[0]
                    var two = substring[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    var result = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    tvInput.text = result
                }
                else if(tvValue.contains("+")){
                    var substring = tvValue.split("+")
                    var one = substring[0]
                    var two = substring[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    var result = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    tvInput.text = result
                }
                else if(tvValue.contains("*")){
                    var substring = tvValue.split("*")
                    var one = substring[0]
                    var two = substring[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    var result = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    tvInput.text = result
                }
                else if(tvValue.contains("/")){
                    var substring = tvValue.split("/")
                    var one = substring[0]
                    var two = substring[1]
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    var result = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    tvInput.text = result
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length-2)
            return value
        }
        return value

    }
    fun onOperator(view: View){

        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            // if last numeric is true and isOperatorAdded is false -> add operator
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        } else value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
    }
}