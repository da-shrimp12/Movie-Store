package com.example.moviestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviestore.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.settings)

    }
}