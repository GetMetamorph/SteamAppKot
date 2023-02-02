package com.example.steamappkot

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.example.steamappkot.databinding.ActivityGameDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding


    private lateinit var switchOnOff: SwitchCompat
    private lateinit var tvSwitchYes: TextView
    private lateinit var tvSwitchNo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchOnOff = findViewById<SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = findViewById<TextView>(R.id.tvSwitchYes)
        tvSwitchNo = findViewById<TextView>(R.id.tvSwitchNo)
        switchOnOff.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    tvSwitchYes.setTextColor(ContextCompat.getColor(this,R.color.white))
                    tvSwitchNo.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
                else -> {
                    tvSwitchYes.setTextColor(ContextCompat.getColor(this, R.color.white))
                    tvSwitchNo.setTextColor(ContextCompat.getColor(this,R.color.white))
                }
            }
        }

        GlobalScope.launch(Dispatchers.Main) {

            try {
                withContext(Dispatchers.IO)
                {
                    val response = CallAPI.getAppDetail("730")
                    Log.i("tag", response.toString())
                    withContext(Dispatchers.Main) {
                        binding.gameDescription.text = response.game.data?.name
                        binding.gameName.text = response.game.data?.name
                        binding.editorsName.text = response.game.data?.publishers?.get(0) ?: ""
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@GameDetailActivity, e.message, Toast.LENGTH_SHORT).show()
            }


        }


    }
}