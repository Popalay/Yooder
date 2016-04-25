package com.popalay.yooder.activities

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import com.popalay.yooder.models.Remind
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_input_message.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class InputMessageActivity : BaseActivity() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager

    companion object {
        val EXTRA_USER_ID = "user_id"

        fun open(context: Context, userId: String) {
            with(context) {
                this.startActivity(intentFor<InputMessageActivity>(EXTRA_USER_ID to userId).newTask().clearTop())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.accept_menu, menu)
        return true;
    }

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Application.graph.inject(this)
        setContentView(R.layout.activity_input_message)
        init()
        initUI()
    }

    private fun init() {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.accept -> {
                if (saveRemind()) {
                    finish()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveRemind(): Boolean {
        var isCorrect = true
        message.error = if (TextUtils.isEmpty(message.text)) {
            isCorrect = false
            "Put your message"
        } else {
            null
        }
        if (isCorrect) {
            val message = message.text.toString().trim()
            val from = socialManager.getMyId()
            val to = userId!!
            dataManager.saveRemind(Remind(message, from, to))
        }
        return isCorrect
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Input message"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userId = intent?.extras?.getString(EXTRA_USER_ID)
        dataManager.getUser(userId!!)
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ user -> to.text = "To: ${user.name}" }, { e -> e.printStackTrace() })
    }
}