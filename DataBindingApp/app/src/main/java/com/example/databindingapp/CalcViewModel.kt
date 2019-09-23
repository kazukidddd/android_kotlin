package com.example.databindingapp

import androidx.databinding.ObservableField
import java.math.MathContext

class CalcViewModel {
    var result = ObservableField("0")
    var mValueOne = 0.0F
    var calcMethod = CalcFlg.NONE

    fun buttonClicked(number: Int) {
        val builder = StringBuilder()

        if (result.get().toString().startsWith("0") && result.get().toString().indexOf(".") != -1) {
            builder.append(result.get()).append(number.toString())
        } else if (result.get().toString().startsWith("0")) {
            builder.append(number.toString())
        } else {
            builder.append(result.get()).append(number.toString())
        }
        result.set(builder.toString())
    }

    fun dotClicked() {
        val regex = result.get()?.indexOf(".")
        val builder = StringBuilder()
        if (result.get()?.isEmpty() == true) {
            builder.append(result.get()).append("0.")
            result.set(builder.toString())
            return
        }
        if (regex == -1) {
            builder.append(result.get()).append(".")
            result.set(builder.toString())
        } else if (regex == 0) {
            builder.append(result.get()).append("0.")
            result.set(builder.toString())
        }
    }

    fun clearClicked() {
        result.set("0")
        calcMethod = CalcFlg.NONE
    }

    fun plusClicked() {
        if (result.get() != "") {
            mValueOne = result.get()?.toFloat() ?: 0.0F
            calcMethod = CalcFlg.PLUS
            result.set("")
        }
    }

    fun minusClicked() {
        if (result.get() != "") {
            mValueOne = result.get()?.toFloat() ?: 0.0F
            calcMethod = CalcFlg.MINUS
            result.set("")
        }
    }

    fun multiplyClicked() {
        if (result.get() != "") {
            mValueOne = result.get()?.toFloat() ?: 0.0F
            calcMethod = CalcFlg.MULTIPLY
            result.set("")
        }
    }

    fun divideClicked() {
        if (result.get() != "") {
            mValueOne = result.get()?.toFloat() ?: 0.0F
            calcMethod = CalcFlg.DIVIDE
            result.set("")
        }
    }

    fun changeMinus() {
        val builder = StringBuilder()
        if (result.get() != "") {
            if (!result.get().toString().startsWith("-")) {
                builder.append(result.get().toString())
                builder.insert(0, "-")
            } else {
                builder.append(result.get().toString())
                builder.deleteCharAt(0)
            }
            result.set(builder.toString())
        }
    }

    fun percentageClicked() {
        if (result.get() != "") {
            mValueOne = result.get()?.toFloat() ?: 0.0F
            result.set((mValueOne * 0.01).toString())
        }
    }

    fun equalClicked() {
        val resultAll: Number
        if (result.get() != "") {
            val mValueTwo = result.get()?.toFloat() ?: 0.0F

            when (calcMethod) {
                CalcFlg.PLUS -> {
                    try {
                        resultAll = (mValueOne.toBigDecimal() + mValueTwo.toBigDecimal()).toFloat()
                        mValueOne = mValueTwo
                        result.set(resultAll.toString())
                    } catch (e:java.lang.NumberFormatException){

                    } catch (e:java.lang.ArithmeticException){

                    }
                }
                CalcFlg.MINUS -> {
                    try {
                        resultAll = (mValueOne.toBigDecimal() - mValueTwo.toBigDecimal()).toFloat()
                        mValueOne = mValueTwo
                        result.set(resultAll.toString())
                    } catch (e:java.lang.NumberFormatException){

                    }catch (e:java.lang.ArithmeticException){

                    }
                }
                CalcFlg.MULTIPLY -> {
                    try {
                        resultAll = (mValueOne.toBigDecimal() * mValueTwo.toBigDecimal()).toFloat()
                        mValueOne = mValueTwo
                        result.set(resultAll.toString())
                    }catch (e:java.lang.NumberFormatException){

                    }catch (e:java.lang.ArithmeticException){

                    }

                }
                CalcFlg.DIVIDE -> {
                    try {
                        resultAll = (mValueOne.toBigDecimal() / mValueTwo.toBigDecimal()).toFloat()
                        mValueOne = mValueTwo
                        result.set(resultAll.toString())
                    }catch (e:java.lang.NumberFormatException){

                    }catch (e:java.lang.ArithmeticException){

                    }

                }

                //何もしないって記述
                else -> Unit
            }
        }
    }
}

enum class CalcFlg{
    NONE,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
}