package com.example.calendar

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ActivitySundaysBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.time.temporal.TemporalQueries.localDate


class Sundays : AppCompatActivity() {

    private lateinit var binding: ActivitySundaysBinding
    val formatter = DateTimeFormatter.ofPattern("dd MMMM")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySundaysBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.SunBackButton.setOnClickListener(){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        val extras = intent.extras ?: return
        val year = extras.getString("Year")?.toInt()

        binding.YearTxt.text = getString(R.string.yearSunday, year.toString())

        val list = getTradeSundays(year)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.TradeSundays.adapter = adapter
    }

    private fun getTradeSundays(year: Int?): MutableList<String> {

        val trades = mutableListOf<LocalDate>()

        for(month in listOf(Month.JANUARY, Month.APRIL, Month.JUNE, Month.AUGUST) )
            getLastSunday(month, year)?.let { trades.add(it) }

        if (year != null) {
            val SunXmas = getSundayBefore(LocalDate.of(year, 12, 25))
            trades.add(SunXmas)
            trades.add(getSundayBefore(SunXmas))
            trades.add(getSundayBefore(HolidaysCalc.EasterDate(year)))
        }

        trades.sort()

        val ret = mutableListOf<String>()
        trades.forEach {
            ret.add(formatter.format(it))
        }

        return  ret
    }

    private fun getLastSunday(month: Month, year: Int?): LocalDate? {

        var date = year?.let { LocalDate.of(it,month,1) }

        if (date != null) {
            date = date.with(TemporalAdjusters.lastDayOfMonth()).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
            //val date1 =  date.with(TemporalAdjusters.lastDayOfMonth()).minusDays(date.dayOfWeek.value.toLong() % 7)
        }
        return date
    }

    private fun getSundayBefore(date: LocalDate): LocalDate {
        //return date.minusDays(date.dayOfWeek.value.toLong())
        return date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
    }

}