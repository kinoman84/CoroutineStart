package ru.buchnev.example.coroutinestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import ru.buchnev.example.coroutinestart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        binding.progressBar.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temperature = loadTemperature(city)
        binding.tvTemperature.text = temperature.toString()
        binding.progressBar.isVisible = false
        binding.buttonLoad.isEnabled = true
    }

    private fun loadCity(): String {
        Thread.sleep(5000)
        return "Moscow"
    }

    private fun loadTemperature(city: String): Int {
        Toast.makeText(
            this,
            "Load temperature for city: $city",
            Toast.LENGTH_SHORT
        ).show()
        Thread.sleep(5000)
        return 17
    }
}