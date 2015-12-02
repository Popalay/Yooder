package com.popalay.yooder.activities

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.popalay.yooder.R
import kotlinx.android.synthetic.dialog_add_debt.*

class AddDebtDialog : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_debt)
        setSupportActionBar(toolbar)
        supportActionBar.title = "New Debt"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back, theme);
        } else {
            toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back);
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dialog_debt, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.save -> {
                save()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun save() {
        //save model
        finish()
    }
}
