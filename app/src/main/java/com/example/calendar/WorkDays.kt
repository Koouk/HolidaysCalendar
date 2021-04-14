package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendar.databinding.ActivityWorkDaysBinding
import java.time.LocalDate
import android.widget.DatePicker
import android.widget.Toast
import java.time.DayOfWeek
import java.time.Month
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
        val holidays = mutableListOf(LocalDate.of(0, Month.JANUARY, 1),
                LocalDate.of(0, Month.JANUARY, 6),
                LocalDate.of(0, Month.MAY, 1),
                LocalDate.of(0, Month.MAY, 3),
                LocalDate.of(0, Month.AUGUST, 15),
                LocalDate.of(0, Month.NOVEMBER, 1),
                LocalDate.of(0, Month.NOVEMBER, 11),
                LocalDate.of(0, Month.DECEMBER , 25),
                LocalDate.of(0, Month.DECEMBER, 26))



        var workDays : Long = 0
        val workWeeks = ChronoUnit.WEEKS.between(startDate, endDate)
        var newDate = startDate.plusWeeks(workWeeks)
        while(!newDate.isEqual(endDate))
        {
            if (checkDate(newDate))
                workDays += 1
            newDate = newDate.plusDays(1)
        }
        workDays += workWeeks * 5


        var currYear = startDate.year
        while(currYear <= endDate.year){

            holidays.forEach {
                val currentHoliday = LocalDate.of(currYear,it.month, it.dayOfMonth)
                if( !checkDate(currentHoliday))
                    return@forEach
                workDays--
            }

            val easter = HolidaysCalc.EasterDate(currYear)

            if(checkDate(easter.plusDays(1)))
                workDays--
            if (checkDate(HolidaysCalc.CorpusDay(easter)))
                workDays--
            currYear++
        }
        return workDays
    }

    private fun checkDate(date : LocalDate) :Boolean
    {
        if( date.dayOfWeek == DayOfWeek.SUNDAY || date.dayOfWeek == DayOfWeek.SATURDAY)
            return false
        if (date.isBefore(startDate) || date.isAfter(endDate))
            return false
        return true
    }

    private fun calculateNormal(): Any {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }


    private var startDate = LocalDate.now()
    private var endDate = LocalDate.now()
}