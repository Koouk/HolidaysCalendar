package com.example.calendar

import java.time.LocalDate
import kotlin.math.floor

class HolidaysCalc {

    companion object {
        fun EasterDate(year : Int) : LocalDate {
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
    }

}