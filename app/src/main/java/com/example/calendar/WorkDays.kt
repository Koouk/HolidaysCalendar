package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendar.databinding.ActivityWorkDaysBinding
import java.time.LocalDate
import android.widget.DatePicker
import android.widget.Toast
import java.time.temporal.ChronoUnit

class WorkDays : AppCompatActivity() {

    private lateinit var binding: ActivityWorkDaysBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkDaysBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.datePickerStart.setOnDateChangedListener{datePicker, year, month, day ->
            startDate = LocalDate.of(year,month + 1,day)
            calculateOutput()
        }
        binding.datePickerEnd.setOnDateChangedListener{datePicker, year, month, day ->
            endDate = LocalDate.of(year,month + 1,day)
            calculateOutput()
        }
        calculateOutput()
    }

    private fun calculateOutput() {
        if (startDate.isAfter(endDate))
        {
            Toast.makeText(getApplicationContext(), R.string.toastMessageDate,
                    Toast.LENGTH_LONG).show();
            binding.daysNormal.text = getString(R.string.wrongDateMessStart)
            binding.daysWork.text = getString(R.string.wrongDateMessEnd)
        }
        else{
            val normalDays = calculateNormal()
            val workDays = calculateWork()
            binding.daysNormal.text = getString(R.string.DateMessStart, normalDays.toString())
            binding.daysWork.text = getString(R.string.DateMessEnd, workDays.toString())
        }
    }

    private fun calculateWork(): Any {
        return 0
    }

    private fun calculateNormal(): Any {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }


    private var startDate = LocalDate.now()
    private var endDate = LocalDate.now()
}