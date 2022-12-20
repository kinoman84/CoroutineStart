package ru.buchnev.example.coroutinestart

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.buchnev.example.coroutinestart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MainActivity", throwable.toString())
    }
    private val myScope = CoroutineScope(Dispatchers.Main + SupervisorJob() + exceptionHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.buttonLoad.isEnabled = false

            val deferredCity = myScope.async {
                val city = loadCity()
                binding.tvLocation.text = city
                city
            }

            val deferredTemperature = myScope.async {
                val temp = loadTemperature()
                binding.tvTemperature.text = temp.toString()
                temp
            }

            /**
             * Запускаем новую корутину, которая дожидается завершения двух дркгх корутин
             * и отрабатывает код по скрытию лоадера
             */
            myScope.launch {
                error()
                val city = deferredCity.await()
                val temp = deferredTemperature.await()
                binding.progressBar.isVisible = false
                binding.buttonLoad.isEnabled = true
                Toast.makeText(
                    this@MainActivity,
                    "City: $city, temperature: $temp",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun error() {
        throw RuntimeException()
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"

    }

    private suspend fun loadTemperature(): Int {
        delay(3000)
        return 17
    }
}