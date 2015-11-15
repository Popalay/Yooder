package com.popalay.yooder.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.fragments.RemindersFragment
import kotlinx.android.synthetic.activity_main.drawerLayout
import kotlinx.android.synthetic.activity_main.navigationView
import kotlinx.android.synthetic.activity_main.toolbar
import kotlinx.android.synthetic.header.email
import kotlinx.android.synthetic.header.username
import org.jetbrains.anko.*

public class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get current user
        if (ParseUser.getCurrentUser() == null) navigateToAuth()
        setSupportActionBar(toolbar)
        initNavigateDrawer()
        setFragment(RemindersFragment(), RemindersFragment.TAG)
        supportActionBar.subtitle = "Reminders"
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
        var actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.widgets_drawer_open, R.string.widgets_drawer_close)
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        setUserInfo()
    }

    private fun setUserInfo() {
        username.text = ParseUser.getCurrentUser()?.getString("FullName")
        email.text = ParseUser.getCurrentUser()?.email
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);
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
            info("setFragment $tag")
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit()
        }
    }

    private fun navigateToAuth() {
        // Launch the login activity
        info("navigateToAuth")
        startActivity(intentFor<AuthActivity>().newTask().clearTop())
        finish()
    }

    private fun logout() {
        ParseUser.logOut()
        navigateToAuth()
    }
}
