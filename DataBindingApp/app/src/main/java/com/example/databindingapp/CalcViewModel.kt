package com.example.databindingapp

import androidx.databinding.ObservableField

class CalcViewModel {
    var result = ObservableField("")
    var mValueOne = 0.0F
    var isPlus = false
    var isMinus = false
    var isWaru = false
    var isKakeru = false
    var isOnce = false

    fun buttonClicked(number : Int){
        val builder = StringBuilder()
        builder.append(result.get()).append(number.toString())
        result.set(builder.toString())
    }

    fun dotClicked(){
        val regex = result.get()?.indexOf(".")
        val builder = StringBuilder()
        if (regex == -1){
            builder.append(result.get()).append(".")
            result.set(builder.toString())
        }
    }

    fun clearClicked(){
        result.set("")
        isOnce = false
        isPlus = false
        isMinus = false
    }

    fun plusClicked(){
        if (result.get() != ""){
            mValueOne = result.get()?.toFloat() ?: 0.0F
            isPlus = true
            result.set("")
        }
    }

    fun minusClicked(){
        if (result.get() != ""){
            mValueOne = result.get()?.toFloat() ?: 0.0F
            isMinus = true
            result.set("")
        }
    }

    fun kakeruClicked(){
        if (result.get() != ""){
            mValueOne = result.get()?.toFloat() ?: 0.0F
            isKakeru = true
            result.set("")
        }
    }

    fun waruClicked(){
        if (result.get() != ""){
            mValueOne = result.get()?.toFloat() ?: 0.0F
            isWaru = true
            result.set("")
        }
    }

    fun equalClicked(){
        var resultAll = 0.0F
        if (result.get() != ""){
                val mValueTwo = result.get()?.toFloat() ?: 0.0F

            when {
                isPlus -> {
                    resultAll = mValueOne + mValueTwo
                    mValueOne = mValueTwo
                    result.set(resultAll.toString())
                    isPlus = false
                }
                isMinus -> {
                    resultAll = mValueOne - mValueTwo
                    mValueOne = mValueTwo
                    result.set(resultAll.toString())
                    isMinus = false
                }
                isKakeru -> {
                    resultAll = mValueOne * mValueTwo
                    mValueOne = mValueTwo
                    result.set(resultAll.toString())
                    isKakeru = false
                }
                isWaru -> {
                    resultAll = mValueOne / mValueTwo
                    mValueOne = mValueTwo
                    result.set(resultAll.toString())
                    isWaru = false
                }
            }
        }

    }
}