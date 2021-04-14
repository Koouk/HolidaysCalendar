package com.example.calendar

import kotlin.math.floor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ActivityMainBinding
import android.os.Bundle
import android.widget.Toast
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val formatter = DateTimeFormatter.ofPattern("dd MMMM YYYY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeNumberPicker()
        calculateHolidays(getYear())
        binding.SundayButton.setOnClickListener { SundayButtonClick() }
        binding.WorkDayButton.setOnClickListener { WorkdayButtonClick() }
    }

    fun initializeNumberPicker(){
        val nPicker = binding.YearPicker
        nPicker.minValue = 1900
        nPicker.maxValue = 2200
        nPicker.wrapSelectorWheel = true
        nPicker.value = 2021


        nPicker.setOnValueChangedListener() { _, _, newVal ->
            calculateHolidays(newVal)

        }
    }
    fun getYear(): Int {
        return binding.YearPicker.value
    }

    fun calculateHolidays(year : Int){
        val easter = HolidaysCalc.EasterDate(year)
        binding.AshValueTxt.text = formatter.format(HolidaysCalc.AshWednesday(easter)).toString()
        binding.EasterValueTxt.text = formatter.format(easter).toString()
        binding.CorpusValueTxt.text = formatter.format(HolidaysCalc.CorpusDay(easter)).toString()
        binding.AdventValueTxt.text = formatter.format(HolidaysCalc.AdventStart(year)).toString()

    }


    fun SundayButtonClick(){
        if (getYear() < 2020) {
            Toast.makeText(getApplicationContext(), R.string.toastMessage,
                    Toast.LENGTH_LONG).show();
            return
        }else {

            val i = Intent(this, Sundays::class.java)
            i.putExtra("Year", getYear().toString())
            startActivity(i)
        }
    }

    fun WorkdayButtonClick(){
        val i = Intent(this, WorkDays::class.java)
        startActivity(i)

    }


}