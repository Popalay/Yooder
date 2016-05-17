package com.popalay.yooder.mvp.createremind

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.arellomobile.mvp.presenter.InjectPresenter
import com.popalay.yooder.R
import com.popalay.yooder.common.BaseActivity
import com.popalay.yooder.models.User
import kotlinx.android.synthetic.main.activity_input_message.*
import org.jetbrains.anko.*


class CreateRemindActivity : BaseActivity(), CreateRemindView {

    @InjectPresenter lateinit var presenter: CreateRemindPresenter

    companion object {

        val EXTRA_USER_ID = "user_id"

        fun open(context: Context, userId: String) {
            with(context) {
                this.startActivity(intentFor<CreateRemindActivity>(EXTRA_USER_ID to userId).newTask().clearTop())
            }
        }
    }

    private var priority: Int = 0

    override fun showError(error: String) {
        message.error = error
    }

    override fun hideError() {
        message.error = null
    }

    override fun onRemindSaved() {
        finish()
    }

    override fun attachUser(user: User) {
        to.text = user.name
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.accept_menu, menu)
        return true;
    }

    private var userId: String? = null

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
            R.id.accept -> {
                saveRemind()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveRemind() {
        presenter.saveRemind(userId!!, message.text.toString().trim(), priority)
        //send event
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Input message"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userId = intent?.extras?.getString(EXTRA_USER_ID)
        if (userId != null) {
            presenter.getUser(userId!!)
        }

        priorityGreen.onClick {
            to.backgroundResource = R.color.green
            animateRevealShow(to)
            priority = 1
        }

        priorityYellow.onClick {
            to.backgroundResource = R.color.yellow
            animateRevealShow(to)
            priority = 2
        }

        priorityOrange.onClick {
            to.backgroundResource = R.color.orange
            animateRevealShow(to)
            priority = 3
        }

        priorityRed.onClick {
            to.backgroundResource = R.color.red
            animateRevealShow(to)
            priority = 4
        }
    }

    private fun animateRevealShow(viewRoot: View) {
        val cx = (viewRoot.left + viewRoot.right) / 2
        val cy = (viewRoot.top + viewRoot.bottom) / 2
        val finalRadius = Math.max(viewRoot.width, viewRoot.height).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0f, finalRadius)
        viewRoot.visibility = View.VISIBLE
        anim.duration = 300
        anim.interpolator = AccelerateInterpolator()
        anim.start()
    }
}