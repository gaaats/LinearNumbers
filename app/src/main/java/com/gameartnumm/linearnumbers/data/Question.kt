package com.gameartnumm.linearnumbers.data

data class Question(val totalSum: Int, val visibleNumber: Int, val listOfVariants: List<Int>){

    val rightAns:Int
    get() = totalSum - visibleNumber

}