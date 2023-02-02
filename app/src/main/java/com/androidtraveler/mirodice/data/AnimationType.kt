package com.androidtraveler.mirodice.data

sealed class AnimationType(val dice: Dice) {
    class Increment(dice: Dice) : AnimationType(dice)
    class Decrement(dice: Dice, val oldDice: Dice) : AnimationType(dice)
}