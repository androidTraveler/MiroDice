package com.androidtraveler.mirodice.ui.dice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiceViewModel : ViewModel() {

    private var dice1value = 1
    private var dice2value = 1
    var value: MutableLiveData<Int> = MutableLiveData()
    var animationState: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val state = MutableLiveData(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    private val range = 1..6

    fun onTossClicked() {
        dice1value = range.random()
        dice2value = range.random()
        animationState.value = dice1value to dice2value
    }
}
