package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.fragments.RemindersFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.view.*

public class MainActivity : BaseActivity() {

    val LOG_TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get current user
        if (ParseUser.getCurrentUser() == null) navigateToAuth()
        else {
            setSupportActionBar(toolbar)
            initNavigateDrawer()
            setFragment(RemindersFragment(), RemindersFragment.TAG)
            supportActionBar.subtitle = "Reminders"
        }
    }

    private fun initNavigateDrawer() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            //Checking if the item is in checked state or not, if not make it in checked state
            //Check to see which item was being clicked and perform appropriate action
            when (menuItem.itemId) {
                R.id.reminders -> {
                    setFragment(RemindersFragment(), RemindersFragment.TAG)
                }
            /*R.id.archive -> {
                //setFragment()
                true
            }*/
                R.id.logout -> {
                    logout()
                }
                else -> {
                    setFragment(RemindersFragment(), RemindersFragment.TAG)
                }
            }
            menuItem.setChecked(true)
            menuItem.setChecked(false)
            supportActionBar.subtitle = menuItem.title
            drawerLayout.closeDrawers();
            true
        }
        // Initializing Drawer Layout and ActionBarToggle
        var actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.widgets_drawer_open, R.string.widgets_drawer_close)
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        setUserInfo()
    }

    private fun setUserInfo() {
        var username = ParseUser.getCurrentUser().getString("FullName") ?: ""
        navigationView.getHeaderView(0).username.text = username
        navigationView.getHeaderView(0).email.text = ParseUser.getCurrentUser().email ?: ""

        var generator = ColorGenerator.MATERIAL
        // generate random color
        var color = generator.getColor(username)

        // reuse the builder specs to create multiple drawables
        var ic = TextDrawable.builder().buildRound(username[0].toString(), color)
        navigationView.getHeaderView(0).icon.setImageDrawable(ic)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item);
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            Log.i(LOG_TAG, "setFragment $tag")
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit()
        }
    }

    private fun navigateToAuth() {
        // Launch the login activity
        Log.i(LOG_TAG, "navigateToAuth")
        var intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent);
        //startActivity(intentFor<AuthActivity>().newTask().clearTop())
        finish()
    }

    private fun logout() {
        ParseUser.logOut()
        navigateToAuth()
    }
}
