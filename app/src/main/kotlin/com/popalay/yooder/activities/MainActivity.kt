package com.popalay.yooder.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.firebase.client.Firebase
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.fragments.NotificationsFragment
import com.popalay.yooder.models.User
import com.soikonomakis.rxfirebase.RxFirebase
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKSdk
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var ref: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Application.graph.inject(this)
        // Get current user
        if (!VKSdk.isLoggedIn()) {
            navigateToAuth()
        } else {
            init()
        }
    }

    private fun init() {
        logger.info("ddd")
        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        bottomNavigation.setDefaultSelectedIndex(1)

        val provider = bottomNavigation.badgeProvider;
        provider.show(R.id.notifications);

        bottomNavigation.setOnMenuItemClickListener(object : BottomNavigation.OnMenuItemSelectionListener {
            override fun onMenuItemSelect(p0: Int, p1: Int) {
                when (p0) {
                    R.id.notifications ->
                        supportFragmentManager.beginTransaction().replace(R.id.container, NotificationsFragment(), NotificationsFragment::class.java.simpleName)
                                .commitAllowingStateLoss()
                }
            }

            override fun onMenuItemReselect(p0: Int, p1: Int) {
            }
        })

        RxFirebase.getInstance().observeSingleValue(ref.child("users").child(VKAccessToken.currentToken().userId))
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .map { dataSnapshot ->
                    dataSnapshot.getValue(User::class.java)
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ user ->
                    Glide.with(this@MainActivity).load(user.photo).bitmapTransform(CropCircleTransformation(this@MainActivity)).into(avatar)
                    name.text = user.name

                }, { error ->
                    error.printStackTrace()
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logout()
                return true
            }
            else -> return super.onOptionsItemSelected(item);
        }
    }

    private fun navigateToAuth() {
        // Launch the login activity
        logger.info("navigateToAuth")
        startActivity(intentFor<AuthActivity>().newTask().clearTop())
        finish()
    }

    private fun logout() {
        VKSdk.logout()
        navigateToAuth()
    }
}
