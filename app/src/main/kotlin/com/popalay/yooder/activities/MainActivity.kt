package com.popalay.yooder.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.popalay.yooder.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class MainActivity : BaseActivity() {

    val LOG_TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get current user
        if (true) navigateToAuth()
        else {
            setSupportActionBar(toolbar)
            supportActionBar?.subtitle = "Reminders"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item);
    }

    private fun navigateToAuth() {
        // Launch the login activity
        Log.i(LOG_TAG, "navigateToAuth")
        startActivity(intentFor<AuthActivity>().newTask().clearTop())
        finish()
    }

    private fun logout() {
        navigateToAuth()
    }
}
