package com.androidtraveler.mirodice.data

data class Dice(val first: Int, val second: Int) {
    val sum = first + second
}