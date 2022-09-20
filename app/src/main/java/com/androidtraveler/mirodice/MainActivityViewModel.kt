package com.androidtraveler.mirodice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    val state = MutableLiveData<MutableList<Int>>(mutableListOf(0,0,0,0,0,0,0,0,0,0,0))

    fun onPlusClicked(position: Int) {
        val list: MutableList<Int>? = state.value
        list?.let {
            var a = it[position-2]
            a++
            it[position - 2] = a
        }
        state.value = list
    }

    fun onMinusClicked(position: Int) {
        val list: MutableList<Int>? = state.value
        list?.let {
            var a = it[position-2]
            a--
            it[position - 2] = a
        }
        state.value = list
    }

}
