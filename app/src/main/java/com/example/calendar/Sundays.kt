package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendar.databinding.ActivitySundaysBinding

class Sundays : AppCompatActivity() {

    private lateinit var binding: ActivitySundaysBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySundaysBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val extras = intent.extras ?: return
        val message = extras.getString("Year")
        binding.textView3.text = message
    }
}