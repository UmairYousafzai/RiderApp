package com.moveitech.riderapp.utils

import android.util.Log
import android.widget.EditText
import androidx.databinding.InverseMethod
import java.text.*
import java.util.*

object Converter {

    var oldNumberString: String =""
    var oldNumber = 0.0

    @InverseMethod("stringToDouble")
    fun doubleToString(num: Double): String {
        return if (num == oldNumber) {
            oldNumberString
        } else {
            oldNumberString = num.toString()
            oldNumberString
        }
    }
    fun stringToDouble(num: String): Double {
        var doubleNum = 0.0
        if (num != oldNumberString) {
            try {
                doubleNum = num.toDouble()
                oldNumber = doubleNum
            } catch (e: Exception) {
                Log.e("Conversion Error:", e.message!!)
            }
        }
        return oldNumber
    }

    fun StringToFormatDate(date: String?): String {
        var formatDate = ""
        if (date != null) {
            var date1: Date? = null
            try {
                date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            if (date1 != null) {
                formatDate = dateFormat.format(date1)
            }
        }
        return formatDate
    }

    fun fullStringToFirstLetter(string: String?): String? {
        var letter = ""
        if (string != null && !string.isEmpty()) {
            letter = string.substring(0, 1)
        }
        return letter
    }

    @InverseMethod("stringToDouble")
    @JvmStatic
    fun numToString(
        view: EditText, oldValue: Double,
        value: Double
    ): String {
        val numberFormat = getNumberFormat(view)
        try {
            // Don't return a different value if the parsed value
            // doesn't change
            val inView = view.text.toString()
            val parsed = numberFormat.parse(inView)?.toDouble()
            if (parsed == value) {
                return view.text.toString()
            }
        } catch (e: ParseException) {
            // Old number was broken
        }
        return numberFormat.format(value)
    }
    @JvmStatic
    fun stringToDouble(
        view: EditText, oldValue: Double,
        value: String?
    ): Double {
        val numberFormat = getNumberFormat(view)
        return try {
            numberFormat.parse(value).toDouble()
        } catch (e: ParseException) {
            val errStr = "bad number"
            view.error = errStr
            oldValue
        }
    }

    private fun getNumberFormat(view: EditText): NumberFormat {
        val resources = view.resources
        val locale = resources.configuration.locale
        val format = NumberFormat.getNumberInstance(locale)
        if (format is DecimalFormat) {
            format.isGroupingUsed = false
        }
        return format
    }

    @JvmStatic
    fun FormatDoubleNumbers(num: Double): String{
        return "Rs " + DecimalFormat("##.##").format(num)
    }

    @JvmStatic
    fun FormatDoubleNumbers(num: String): String {
        var num = num
        val numberArray = num.split("\\.").toTypedArray()
        if (numberArray.size > 1) {
            if (numberArray[1].length > 2) {
                num = numberArray[0] + "." + numberArray[1].substring(0, 2)
            }
        }
        return num
    }


}