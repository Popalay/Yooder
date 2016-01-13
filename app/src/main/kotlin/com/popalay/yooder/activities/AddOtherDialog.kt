package com.popalay.yooder.activities

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.eventbus.AddedOtherEvent
import com.popalay.yooder.eventbus.BusProvider
import com.popalay.yooder.models.Other
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.dialog_add_other.*
import java.text.SimpleDateFormat
import java.util.*

class AddOtherDialog : BaseActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    val TAG = AddOtherDialog::class.java.simpleName

    var dateStr: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_other)
        baseInit()
    }

    private fun baseInit() {
        setSupportActionBar(toolbar)
        supportActionBar.title = "New Remind"
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        date.editText.setOnFocusChangeListener { view, b ->
            if (b) {
                var now = Calendar.getInstance();
                DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                ).show(fragmentManager, "Datepickerdialog");
            }
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateStr = "$dayOfMonth.${monthOfYear + 1}.$year"
        var now = Calendar.getInstance();
        TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        ).show(fragmentManager, "Timepickerdialog");
        date.clearFocus();
    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        dateStr += " $hourOfDay:$minute"
        date.editText.setText(dateStr)
        date.clearFocus();
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
        var other = Other()
        var isOk = true
        other.author = ParseUser.getCurrentUser()
        titleReminder.error = if (TextUtils.isEmpty(titleReminder.editText.text)) {
            isOk = false
            getString(R.string.error_empty)
        } else null
        description.error = if (TextUtils.isEmpty(description.editText.text)) {
            isOk = false
            getString(R.string.error_empty)
        } else null
        var newDate: Date? = null
        if (!TextUtils.isEmpty(date.editText.text)) {
            newDate = SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date.editText.text.toString())
            date.error = if (!newDate.after(Date())) {
                isOk = false
                getString(R.string.error_illegal)
            } else null
        }
        if (isOk) {
            other.title = titleReminder.editText.text.toString()
            other.description = description.editText.text.toString()
            other.date = newDate
            other.saveEventually()
            BusProvider.bus.post(AddedOtherEvent(other))
            finish()
        }
    }
}
