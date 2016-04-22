package com.popalay.yooder.activities

import android.os.Bundle
import android.view.MenuItem
import com.popalay.yooder.R
import kotlinx.android.synthetic.main.activity_choose_friend.*


class InputMessageActivity :BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_message)
        initUI()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Input message"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}