package com.popalay.yooder.mvp.main

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.popalay.yooder.R
import com.popalay.yooder.common.BaseActivity
import com.popalay.yooder.fragments.FeedFragment
import com.popalay.yooder.fragments.NotificationsFragment
import com.popalay.yooder.models.User
import com.popalay.yooder.mvp.auth.AuthActivity
import com.popalay.yooder.mvp.choosefriend.ChooseFriendActivity
import com.popalay.yooder.widgets.PagerAdapter
import com.yalantis.guillotine.animation.GuillotineAnimation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.guillotine.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.onClick

class MainActivity : BaseActivity(), MainView {
    @InjectPresenter lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = null


        val viewPagerAdapter = PagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFrag(FeedFragment(), getString(R.string.archive))
        viewPagerAdapter.addFrag(FeedFragment(), getString(R.string.feed))
        viewPagerAdapter.addFrag(NotificationsFragment(), getString(R.string.notifications))
        pager.adapter = viewPagerAdapter

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                presenter.selectPage(position)
            }
        })

        val colors = arrayOf(ContextCompat.getColor(this, R.color.fiolet), ContextCompat.getColor(this, R.color.primary),
                ContextCompat.getColor(this, R.color.blue)).toIntArray()
        val icons = arrayOf(R.drawable.archive, R.drawable.feed, R.drawable.notifications).toIntArray()
        bottomNavigation.setUpWithViewPager(pager, colors, icons);
        bottomNavigation.isWithText(false)
        bottomNavigation.selectTab(1)

        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null)
        guillotineMenu.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        root.addView(guillotineMenu);
        val animationMenu = GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build()

        addReminder.onClick {
            animationMenu.close()
            presenter.addRemind()
        }
        logout.onClick {
            animationMenu.close()
            presenter.logout()
        }
    }

    override fun onReturnCurrentUser(user: User) {
        Glide.with(this@MainActivity).load(user.photo).bitmapTransform(CropCircleTransformation(this@MainActivity)).into(avatar)
        name.text = user.name
    }

    override fun navigateToAuthScreen() {
        startActivity(intentFor<AuthActivity>().newTask().clearTop())
        finish()
    }

    override fun openChooseFriendScreen() {
        ChooseFriendActivity.open(this)
    }

    override fun onPageSelected(position: Int) {
        bottomNavigation.selectTab(position)
    }
}
