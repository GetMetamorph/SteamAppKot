package com.example.steamappkot

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.steamappkot.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        GlobalScope.launch(Dispatchers.Main) {

            try {
                withContext(Dispatchers.IO)
                {
                    val response = CallAPI.getMostPlayedGames()
                    Log.i("tag", response.toString())
                    withContext((Dispatchers.Main)) {
                    }
                    var games = ArrayList<SteamAppResponse>()
                    for (ids in response) {
                        games.add(CallAPI.getAppDetail(ids.appid.toString()))
                    }
                    withContext(Dispatchers.Main) {
                        Glide.with(applicationContext).load(games[0].game.data?.header_image).into(binding.itemImagedestiny1)
                        binding.itemText1name1.text = games[0].game.data?.name ?: ""
                        binding.itemText2name1.text = games[0].game.data?.publishers?.get(0) ?: ""
                        if(games[0].game.data?.is_free == true) {
                            binding.itemText3name1.text = "Gratuit"
                        } else {
                            binding.itemText3name1.text = "Prix : " + games[0].game.data?.price_overview?.final_formatted
                        }
                        val button1: Button = findViewById(R.id.item_buttonmore1)
                        button1.setOnClickListener {
                            val intent = Intent(this@MainActivity, GameDetailActivity::class.java)
                            startActivity(intent)
                        }

                        Glide.with(applicationContext).load(games[1].game.data?.header_image).into(binding.itemImagedestiny2)
                        binding.itemText1name2.text = games[1].game.data?.name ?: ""
                        binding.itemText2name2.text = games[1].game.data?.publishers?.get(0) ?: ""
                        if(games[1].game.data?.is_free == true) {
                            binding.itemText3name2.text = "Gratuit"
                        } else {
                            binding.itemText3name2.text = "Prix : " + games[1].game.data?.price_overview?.final_formatted
                        }
                        val button2: Button = findViewById(R.id.item_buttonmore2)
                        button2.setOnClickListener { }

                        Glide.with(applicationContext).load(games[2].game.data?.header_image).into(binding.itemImagedestiny3)
                        binding.itemText1name3.text = games[2].game.data?.name ?: ""
                        binding.itemText2name3.text = games[2].game.data?.publishers?.get(0) ?: ""
                        if(games[2].game.data?.is_free == true) {
                            binding.itemText3name3.text = "Gratuit"
                        } else {
                            binding.itemText3name3.text = "Prix : " + games[2].game.data?.price_overview?.final_formatted
                        }
                        val button3: Button = findViewById(R.id.item_buttonmore3)
                        button3.setOnClickListener { }

                        Glide.with(applicationContext).load(games[3].game.data?.header_image).into(binding.itemImagedestiny4)
                        binding.itemText1name4.text = games[3].game.data?.name ?: ""
                        binding.itemText2name4.text = games[3].game.data?.publishers?.get(0) ?: ""
                        if(games[3].game.data?.is_free == true) {
                            binding.itemText3name4.text = "Gratuit"
                        } else {
                            binding.itemText3name4.text = "Prix : " + games[3].game.data?.price_overview?.final_formatted
                        }
                        val button4: Button = findViewById(R.id.item_buttonmore4)
                        button4.setOnClickListener { }

                        Glide.with(applicationContext).load(games[4].game.data?.header_image).into(binding.itemImagedestiny5)
                        binding.itemText1name5.text = games[4].game.data?.name ?: ""
                        binding.itemText2name5.text = games[4].game.data?.publishers?.get(0) ?: ""
                        if(games[4].game.data?.is_free == true) {
                            binding.itemText3name5.text = "Gratuit"
                        } else {
                            binding.itemText3name5.text = "Prix : " + games[4].game.data?.price_overview?.final_formatted
                        }
                        val button5: Button = findViewById(R.id.item_buttonmore5)
                        button5.setOnClickListener { }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}