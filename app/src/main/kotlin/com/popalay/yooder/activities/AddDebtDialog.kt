package com.popalay.yooder.activities

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding.widget.RxTextView
import com.parse.ParseUser
import com.popalay.yooder.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.dialog_add_debt.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit

class AddDebtDialog : BaseActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    val TAG = AddDebtDialog::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_debt)
        baseInit()
    }

    private fun baseInit() {
        setSupportActionBar(toolbar)
        supportActionBar.title = "New Debt"
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        btnSetDate.setOnClickListener {
            var now = Calendar.getInstance();
            DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            ).show(fragmentManager, "Datepickerdialog");
        }
        btnSetTime.setOnClickListener {
            var now = Calendar.getInstance();
            TimePickerDialog.newInstance(
                    this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
            ).show(fragmentManager, "Timepickerdialog");
        }

        party.requestFocus()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        actvParty.setAdapter(adapter)
        RxTextView.afterTextChangeEvents(actvParty)
               // .filter { actvParty.text.count() > 0 }
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    ParseUser.getQuery().whereContains("FullName", actvParty.text.toString())
                            .findInBackground { users, e ->
                                adapter.clear()
                                adapter.addAll(users.map { user -> user.getString("FullName")  + "\n" + user.username})
                                actvParty.showDropDown()
                            }
                }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        btnSetDate.text = "$dayOfMonth.${monthOfYear + 1}.$year"
    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        btnSetTime.text = "$hourOfDay : $minute"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dialog_debt, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this);
                return true
            }
            R.id.save -> {
                save()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun save() {
        //save model
        finish()
    }
}
