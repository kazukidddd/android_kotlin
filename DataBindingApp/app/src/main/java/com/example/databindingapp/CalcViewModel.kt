package com.example.databindingapp

class CalcViewModel {
    var result = "0"

    fun buttonClicked(number :Int){
        result = number.toString()
    }
}