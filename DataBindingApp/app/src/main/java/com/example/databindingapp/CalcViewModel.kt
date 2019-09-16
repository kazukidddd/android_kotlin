package com.example.databindingapp

import androidx.databinding.ObservableField

class CalcViewModel {
    var result = ObservableField("")
    var mValueOne = 0.0F
    var mValueTwo = 0.0F
    var isPlus = false
    var isMinus = false
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

    fun equalClicked(){
        if (result.get() != ""){
            if (!isOnce){
                mValueTwo = result.get()?.toFloat() ?: 0.0F
                isOnce = true
            }

            if (isPlus){
                var resultAll = mValueOne + mValueTwo
                mValueOne = mValueTwo
                result.set(resultAll.toString())
            }else if (isMinus){
                var resultAll = mValueOne - mValueTwo
                result.set(resultAll.toString())
            }
        }

    }
}