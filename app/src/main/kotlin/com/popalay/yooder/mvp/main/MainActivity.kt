package com.popalay.yooder.mvp.main

import android.os.Bundle
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
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
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

        bottomNavigation.setDefaultSelectedIndex(1)

        val provider = bottomNavigation.badgeProvider;
        provider.show(R.id.notifications);

        val viewPagerAdapter = PagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFrag(FeedFragment())
        viewPagerAdapter.addFrag(FeedFragment())
        viewPagerAdapter.addFrag(NotificationsFragment())
        pager.adapter = viewPagerAdapter
        pager.currentItem = 1
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                presenter.selectPage(position)
            }
        })
        bottomNavigation.setOnMenuItemClickListener(object : BottomNavigation.OnMenuItemSelectionListener {
            override fun onMenuItemSelect(p0: Int, p1: Int) {
                presenter.selectPage(p1)
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
        bottomNavigation.setSelectedIndex(position, true)
        pager.setCurrentItem(position, true)
    }
}
