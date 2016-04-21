package com.popalay.yooder.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.yalantis.guillotine.animation.GuillotineAnimation
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.guillotine.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.onClick
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
            initUI()
            init()
        }
    }

    private fun initUI() {
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

        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null)
        guillotineMenu.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        root.addView(guillotineMenu);
        val animationMenu = GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build()

        addReminder.onClick {
            animationMenu.close()
            //add
        }
        logout.onClick {
            animationMenu.close()
            logout()
        }
    }

    private fun init() {
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
