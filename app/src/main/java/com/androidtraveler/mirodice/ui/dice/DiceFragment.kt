package com.androidtraveler.mirodice.ui.dice

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.androidtraveler.mirodice.R
import com.androidtraveler.mirodice.databinding.FragmentDiceBinding
import com.androidtraveler.mirodice.ui.base.BaseFragment

class DiceFragment : BaseFragment<FragmentDiceBinding>(
    R.layout.fragment_dice,
    FragmentDiceBinding::inflate
) {

    private lateinit var viewModel: DiceViewModel
    private val rotationSet = AnimatorSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DiceViewModel::class.java]
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //TODO: handle double click with dialog to exit from app
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        initAnimations()
    }

    private fun initListeners() {
        binding.btnToss.setOnClickListener {
            viewModel.animationState.value?.let {
                setInImage(binding.ivDice1, it.first)
                setInImage(binding.ivDice2, it.second)
            }
            viewModel.onTossClicked()
        }
    }

    private fun initObservers() {
        viewModel.animationState.observe(viewLifecycleOwner) {
            startAnimation(it)
        }

        viewModel.value.observe(viewLifecycleOwner) {
            binding.tvTTT.text = it.toString()
            val list: MutableList<Int>? = viewModel.state.value
            list?.let { items ->
                var a = items[it - 2]
                a++
                items[it - 2] = a
            }
            viewModel.state.value = list
        }
        viewModel.state.observe(viewLifecycleOwner) {
            binding.tv2.text = it[0].toString()
            binding.tv3.text = it[1].toString()
            binding.tv4.text = it[2].toString()
            binding.tv5.text = it[3].toString()
            binding.tv6.text = it[4].toString()
            binding.tv7.text = it[5].toString()
            binding.tv8.text = it[6].toString()
            binding.tv9.text = it[7].toString()
            binding.tv10.text = it[8].toString()
            binding.tv11.text = it[9].toString()
            binding.tv12.text = it[10].toString()
        }
    }

    private fun initAnimations() = rotationSet.playTogether(
        ObjectAnimator.ofFloat(binding.ivDice1, "rotation", 0f, 360f).setDuration(500),
        ObjectAnimator.ofFloat(binding.ivDice2, "rotation", 0f, 360f).setDuration(500)
    )

    private fun setInImage(iv: ImageView, dice: Int) {
        when (dice) {
            1 -> iv.setImageResource(R.drawable.anim_1)
            2 -> iv.setImageResource(R.drawable.anim_2_in)
            3 -> iv.setImageResource(R.drawable.anim_3_in)
            4 -> iv.setImageResource(R.drawable.anim_4_in)
            5 -> iv.setImageResource(R.drawable.anim_5_in)
            6 -> iv.setImageResource(R.drawable.anim_6_in)
        }
    }

    private fun setOutImage(iv: ImageView, dice: Int) {
        when (dice) {
            1 -> iv.setImageResource(R.drawable.anim_1)
            2 -> iv.setImageResource(R.drawable.anim_2_out)
            3 -> iv.setImageResource(R.drawable.anim_3_out)
            4 -> iv.setImageResource(R.drawable.anim_4_out)
            5 -> iv.setImageResource(R.drawable.anim_5_out)
            6 -> iv.setImageResource(R.drawable.anim_6_out)
        }
    }

    private fun startAnimation(dices: Pair<Int, Int>) {
        animateDrawables()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            rotationSet.start()
            handler.postDelayed({
                setOutImage(binding.ivDice1, dices.first)
                setOutImage(binding.ivDice2, dices.second)
                animateDrawables()
                viewModel.value.postValue(dices.first + dices.second)
            }, 500)
        }, 500)
    }

    private fun animateDrawables() {
        (binding.ivDice1.drawable as AnimatedVectorDrawable).start()
        (binding.ivDice2.drawable as AnimatedVectorDrawable).start()
    }

}