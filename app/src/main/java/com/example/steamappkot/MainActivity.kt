package com.example.steamappkot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.steamappkot.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.Main) {

            try {
                withContext(Dispatchers.IO)
                {
                    val response = CallAPI.getMostPlayedGames()
                    Log.i("tag", response.toString())
                    withContext((Dispatchers.Main)) {
                        binding.progressBar.visibility = View.GONE
                        binding.alertMessage.text = response.toString()
                    }
                }   // end of withContext
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}