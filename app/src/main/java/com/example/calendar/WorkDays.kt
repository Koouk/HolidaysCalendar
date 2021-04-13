package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendar.databinding.ActivityWorkDaysBinding

class WorkDays : AppCompatActivity() {

    private lateinit var binding: ActivityWorkDaysBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkDaysBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val extras = intent.extras ?: return
        val message = extras.getString("Year")
        binding.textView2.text = message

    }
}