package com.example.calendar

import kotlin.math.floor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ActivityMainBinding
import android.os.Bundle
import java.time.DayOfWeek
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeNumberPicker()

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
        val result = StringBuilder()
        val easter = EasterDate(year)
        result.append("Swieta w $year: \n")
        result.append("Popielec: ").append(AshWednesday(easter)).append("\n")
        result.append("Wielkanoc: ").append(easter).append("\n")
        result.append("Boze Cialo: ").append(CorpusDay(easter)).append("\n")
        result.append("Początek adwentu: ").append(AdventStart(year)).append("\n")

        binding.HolidayTextField.text = result
    }

    fun EasterDate(year : Int) : LocalDate{
        val a : Int = year % 19
        val b : Int = floor(year / 100.0).toInt()
        val c : Int = year % 100
        val d : Int = floor(b / 4.0).toInt()
        val e : Int = b % 4
        val f : Int = ((b + 8) / 25.0).toInt()
        val g : Int = ((b - f + 1) / 3.0).toInt()
        val h : Int = (19 * a + b - d - g + 15) % 30
        val i : Int = floor(c / 4.0).toInt()
        val k : Int = c % 4
        val l : Int = (32 + 2 * e + 2 * i - h - k) % 7
        val m : Int = (floor(a + 11.0 * h + 22.0 * l) / 451.0).toInt()
        val p : Int = (h + l - 7 * m + 114) % 31
        val day = p + 1
        val month = ((h + l - 7.0 * m + 114.0) / 31.0).toInt()
        return LocalDate.of(
            year,
            month,
            day
        )
    }

    fun AshWednesday(date : LocalDate) : LocalDate
    {
        return date.minusDays(46)
    }

    fun CorpusDay(date : LocalDate) : LocalDate
    {
        return date.plusDays(60)
    }

    fun AdventStart(year : Int) : LocalDate
    {
        var advent = LocalDate.of(year,12,25).minusDays(3 * 7)
        advent = advent.minusDays(advent.dayOfWeek.value.toLong())
        return advent
    }


    fun SundayButtonClick(){
        val i = Intent(this, Sundays::class.java)
        i.putExtra("Year", getYear().toString())
        startActivity(i)

    }

    fun WorkdayButtonClick(){
        val i = Intent(this, WorkDays::class.java)
        i.putExtra("Year", getYear().toString())
        startActivity(i)

    }


}