package com.androidtraveler.mirodice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        initUI()
        initObservers()
        initListeners()
    }

    private fun initUI() {

    }

    private fun initObservers() {
        viewModel.state.observe(this) {
            findViewById<TextView>(R.id.tv2).text = it[0].toString()
            findViewById<TextView>(R.id.tv3).text = it[1].toString()
            findViewById<TextView>(R.id.tv4).text = it[2].toString()
            findViewById<TextView>(R.id.tv5).text = it[3].toString()
            findViewById<TextView>(R.id.tv6).text = it[4].toString()
            findViewById<TextView>(R.id.tv7).text = it[5].toString()
            findViewById<TextView>(R.id.tv8).text = it[6].toString()
            findViewById<TextView>(R.id.tv9).text = it[7].toString()
            findViewById<TextView>(R.id.tv10).text = it[8].toString()
            findViewById<TextView>(R.id.tv11).text = it[9].toString()
            findViewById<TextView>(R.id.tv12).text = it[10].toString()
        }
    }

    private fun initListeners() {
        findViewById<ImageButton>(R.id.minus2).setOnClickListener { viewModel.onMinusClicked(2) }
        findViewById<ImageButton>(R.id.plus2).setOnClickListener { viewModel.onPlusClicked(2) }

        findViewById<ImageButton>(R.id.minus3).setOnClickListener { viewModel.onMinusClicked(3) }
        findViewById<ImageButton>(R.id.plus3).setOnClickListener { viewModel.onPlusClicked(3) }

        findViewById<ImageButton>(R.id.minus4).setOnClickListener { viewModel.onMinusClicked(4) }
        findViewById<ImageButton>(R.id.plus4).setOnClickListener { viewModel.onPlusClicked(4) }

        findViewById<ImageButton>(R.id.minus5).setOnClickListener { viewModel.onMinusClicked(5) }
        findViewById<ImageButton>(R.id.plus5).setOnClickListener { viewModel.onPlusClicked(5) }

        findViewById<ImageButton>(R.id.minus6).setOnClickListener { viewModel.onMinusClicked(6) }
        findViewById<ImageButton>(R.id.plus6).setOnClickListener { viewModel.onPlusClicked(6) }

        findViewById<ImageButton>(R.id.minus7).setOnClickListener { viewModel.onMinusClicked(7) }
        findViewById<ImageButton>(R.id.plus7).setOnClickListener { viewModel.onPlusClicked(7) }

        findViewById<ImageButton>(R.id.minus8).setOnClickListener { viewModel.onMinusClicked(8) }
        findViewById<ImageButton>(R.id.plus8).setOnClickListener {  viewModel.onPlusClicked(8)}

        findViewById<ImageButton>(R.id.minus9).setOnClickListener {viewModel.onMinusClicked(9) }
        findViewById<ImageButton>(R.id.plus9).setOnClickListener { viewModel.onPlusClicked(9) }

        findViewById<ImageButton>(R.id.minus10).setOnClickListener { viewModel.onMinusClicked(10) }
        findViewById<ImageButton>(R.id.plus10).setOnClickListener { viewModel.onPlusClicked(10) }

        findViewById<ImageButton>(R.id.minus11).setOnClickListener { viewModel.onMinusClicked(11) }
        findViewById<ImageButton>(R.id.plus11).setOnClickListener { viewModel.onPlusClicked(11) }

        findViewById<ImageButton>(R.id.minus12).setOnClickListener { viewModel.onMinusClicked(12) }
        findViewById<ImageButton>(R.id.plus12).setOnClickListener { viewModel.onPlusClicked(12) }
    }
}