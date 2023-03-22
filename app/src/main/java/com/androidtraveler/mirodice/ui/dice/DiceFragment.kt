package com.androidtraveler.mirodice.ui.dice

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androidtraveler.mirodice.R
import com.androidtraveler.mirodice.data.AnimationType
import com.androidtraveler.mirodice.data.Dice
import com.androidtraveler.mirodice.databinding.FragmentDiceBinding
import com.androidtraveler.mirodice.extensions.delayOnLifecycle
import com.androidtraveler.mirodice.ui.base.BaseFragment
import kotlinx.coroutines.launch

class DiceFragment : BaseFragment<FragmentDiceBinding>(
    R.layout.fragment_dice,
    FragmentDiceBinding::inflate
) {

    private lateinit var viewModel: DiceViewModel
    private val rotationSet = AnimatorSet()
    private val mp by lazy { MediaPlayer.create(context, R.raw.dice_sound) }

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
            setInImages()
            viewModel.onTossClicked()
        }
        binding.ivRevert.setOnClickListener {
            setInImages()
            viewModel.onRevertClicked()
        }
    }

    private fun setInImages() = viewModel.animationState.value?.let {
        setInImage(binding.ivDice1, it.dice.first)
        setInImage(binding.ivDice2, it.dice.second)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.buttonState.collect {
                    binding.btnToss.isClickable = it
                    binding.ivRevert.isClickable = it
                }
            }
        }
        viewModel.animationState.observe(viewLifecycleOwner) {
            when (it) {
                is AnimationType.Increment -> playIncrementAnimation(it.dice)
                is AnimationType.Decrement -> playDecrementAnimation(it.dice, it.oldDice)
            }
        }
        viewModel.displayValue.observe(viewLifecycleOwner) {
            binding.tvResult.text = it.toString()
        }
        viewModel.chartState.observe(viewLifecycleOwner) {
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

    private fun setInImage(iv: ImageView, dice: Int) = when (dice) {
        1 -> iv.setImageResource(R.drawable.anim_1)
        2 -> iv.setImageResource(R.drawable.anim_2_in)
        3 -> iv.setImageResource(R.drawable.anim_3_in)
        4 -> iv.setImageResource(R.drawable.anim_4_in)
        5 -> iv.setImageResource(R.drawable.anim_5_in)
        6 -> iv.setImageResource(R.drawable.anim_6_in)
        else -> {}
    }

    private fun setOutImage(iv: ImageView, dice: Int) = when (dice) {
        1 -> iv.setImageResource(R.drawable.anim_1)
        2 -> iv.setImageResource(R.drawable.anim_2_out)
        3 -> iv.setImageResource(R.drawable.anim_3_out)
        4 -> iv.setImageResource(R.drawable.anim_4_out)
        5 -> iv.setImageResource(R.drawable.anim_5_out)
        6 -> iv.setImageResource(R.drawable.anim_6_out)
        else -> {}
    }

    private fun playIncrementAnimation(dice: Dice) {
        animateDrawables()
        view?.let {
            it.delayOnLifecycle(500) {
                rotationSet.start()
                mp.start()
                it.delayOnLifecycle(500) {
                    setOutImage(binding.ivDice1, dice.first)
                    setOutImage(binding.ivDice2, dice.second)
                    animateDrawables()
                    viewModel.incrementData()
                }
            }
        }
    }

    private fun playDecrementAnimation(dice: Dice, oldDice: Dice) {
        animateDrawables()
        view?.delayOnLifecycle(500) {
            setOutImage(binding.ivDice1, dice.first)
            setOutImage(binding.ivDice2, dice.second)
            animateDrawables()
            viewModel.decrementData(oldDice)
        }
    }

    private fun animateDrawables() {
        (binding.ivDice1.drawable as AnimatedVectorDrawable).start()
        (binding.ivDice2.drawable as AnimatedVectorDrawable).start()
    }

}