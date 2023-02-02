package com.androidtraveler.mirodice.ui.dice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidtraveler.mirodice.data.AnimationType
import com.androidtraveler.mirodice.data.Dice
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    var displayValue: MutableLiveData<Int> = MutableLiveData()
    var animationState: MutableLiveData<AnimationType> = MutableLiveData()
    val chartState = MutableLiveData(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    private val sequence = ArrayDeque<Dice>()
    private val range = 1..6

    fun onTossClicked() {
        val random = Random(System.currentTimeMillis())
        val newDice = Dice(range.random(random), range.random(random))
        sequence.addLast(newDice)
        animationState.value = AnimationType.Increment(newDice)
    }

    fun onRevertClicked() {
        val oldDice = sequence.removeLastOrNull() ?: return
        val dice = sequence.lastOrNull() ?: Dice(1, 1)
        animationState.value = AnimationType.Decrement(dice, oldDice)
    }

    fun incrementData() {
        val list: MutableList<Int>? = chartState.value
        list?.let { items ->
            val value = sequence.last().sum
            var a = items[value - 2]
            a++
            items[value - 2] = a
        }
        chartState.value = list
    }

    fun decrementData(dice: Dice) {
        val list: MutableList<Int>? = chartState.value
        list?.let { items ->
            var a = items[dice.sum - 2]
            a--
            items[dice.sum - 2] = a
        }
        chartState.value = list
        updateDisplayValue()
    }

    fun updateDisplayValue() = displayValue.postValue(sequence.lastOrNull()?.sum ?: 0)
}
