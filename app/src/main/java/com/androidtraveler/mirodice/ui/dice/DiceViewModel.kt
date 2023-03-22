package com.androidtraveler.mirodice.ui.dice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidtraveler.mirodice.data.AnimationType
import com.androidtraveler.mirodice.data.Dice
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    private val _buttonState = MutableSharedFlow<Boolean>()
    val buttonState = _buttonState.asSharedFlow()

    var displayValue: MutableLiveData<Int> = MutableLiveData()
    var animationState: MutableLiveData<AnimationType> = MutableLiveData()
    val chartState = MutableLiveData(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    private val sequence = ArrayDeque<Dice>()
    private val range = 1..6

    fun onTossClicked() {
        enableButtons(false)
        val random = Random(System.currentTimeMillis())
        val newDice = Dice(range.random(random), range.random(random))
        sequence.addLast(newDice)
        animationState.value = AnimationType.Increment(newDice)
    }

    fun onRevertClicked() {
        val oldDice = sequence.removeLastOrNull() ?: return
        enableButtons(false)
        val dice = sequence.lastOrNull() ?: Dice(1, 1)
        animationState.value = AnimationType.Decrement(dice, oldDice)
    }

    fun incrementData() {
        updateDisplayValue()
        val list: MutableList<Int>? = chartState.value
        list?.let { items ->
            val value = sequence.last().sum
            var a = items[value - 2]
            a++
            items[value - 2] = a
        }
        chartState.value = list
        enableButtons(true)
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
        enableButtons(true)
    }

    private fun enableButtons(enable: Boolean) = viewModelScope.launch { _buttonState.emit(enable) }

    private fun updateDisplayValue() = displayValue.postValue(sequence.lastOrNull()?.sum ?: 0)
}
