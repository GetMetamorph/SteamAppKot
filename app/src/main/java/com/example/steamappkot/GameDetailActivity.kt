package com.example.steamappkot

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.steamappkot.databinding.ActivityGameDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class GameDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding

    private lateinit var gameId: String

    private lateinit var switchOnOff: SwitchCompat
    private lateinit var tvSwitchYes: TextView
    private lateinit var tvSwitchNo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameId = intent.getStringExtra("game_id").toString()

        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gameDescription.visibility = TextView.VISIBLE
        binding.reviews.visibility = LinearLayout.INVISIBLE
        switchOnOff = findViewById<SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = findViewById<TextView>(R.id.tvSwitchYes)
        tvSwitchNo = findViewById<TextView>(R.id.tvSwitchNo)
        switchOnOff.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    tvSwitchYes.setTextColor(ContextCompat.getColor(this,R.color.white))
                    tvSwitchNo.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.gameDescription.visibility = TextView.INVISIBLE
                    binding.reviews.visibility = LinearLayout.VISIBLE
                }
                else -> {
                    tvSwitchYes.setTextColor(ContextCompat.getColor(this, R.color.white))
                    tvSwitchNo.setTextColor(ContextCompat.getColor(this,R.color.white))
                    binding.gameDescription.visibility = TextView.VISIBLE
                    binding.reviews.visibility = LinearLayout.INVISIBLE
                }
            }
        }

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO)
                {
                    val response = CallAPI.getAppDetail(gameId.toString())
                    Log.i("tag", response.toString())
                    withContext(Dispatchers.Main) {
                        Glide.with(applicationContext).load(response.game.data?.header_image).into(binding.itemImageDetail)
                        Glide.with(applicationContext).load(response.game.data?.header_image).into(binding.gameImage)
                        binding.gameDescription.text = response.game.data?.detailed_description
                        binding.gameName.text = response.game.data?.name
                        binding.editorsName.text = response.game.data?.publishers?.get(0) ?: ""
                    }

                    val appId = gameId.toString()
                    val url = "https://store.steampowered.com/appreviews/$appId?json=1"

                    val request = Request.Builder()
                        .url(url)
                        .build()

                    val client = OkHttpClient()
                    val response2 = client.newCall(request).execute()

                    val json = response2.body?.string()
                    Log.i("tag", json.toString())
                    println(json)
                }
            } catch (e: Exception) {
                Toast.makeText(this@GameDetailActivity, e.message, Toast.LENGTH_SHORT).show()
            }


        }


    }
}