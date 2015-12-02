package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.fragments.RemindersFragment
import kotlinx.android.synthetic.activity_main.*

public class MainActivity : BaseActivity() {

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
        val username = navigationView.getHeaderView(0).findViewById(R.id.username) as TextView
        val email = navigationView.getHeaderView(0).findViewById(R.id.email) as TextView
        username.text = ParseUser.getCurrentUser()?.getString("FullName") ?: ""
        email.text = ParseUser.getCurrentUser()?.email ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId;
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            Log.i("Main", "setFragment $tag")
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit()
        }
    }

    private fun navigateToAuth() {
        // Launch the login activity
        Log.i("Main", "navigateToAuth")
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
