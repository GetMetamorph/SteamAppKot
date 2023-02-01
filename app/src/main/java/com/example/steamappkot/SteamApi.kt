package com.example.steamappkot

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Rank (
    val rank: Int,
    val appid: Int,
    val last_week_rank: Int,
    val peak_in_game: Int,
)

data class Response(
    val rollup_date: Int,
    val ranks: ArrayList<Rank>
)
val apiKey: String = System.getenv("STEAM_API_KEY")

interface SteamAPI {

    //In get add the follow of the url ex: https://https://api.steampowered.com/ become https://https://api.steampowered.com/GetMostPlayedGames/
    @GET("ISteamChartsService/GetMostPlayedGames/v1/?")
    fun getMostPlayedGames() : Deferred<List<Response>>

    //https://api.steampowered.com/ISteamChartsService/GetMostPlayedGames/v1/?
}

object CallAPI {
    private val api = Retrofit.Builder()
        .baseUrl("https://https://api.steampowered.com/")
        .addConverterFactory(
            GsonConverterFactory.create())
        .addCallAdapterFactory(
            CoroutineCallAdapterFactory()
        )
        .build()
        .create(SteamAPI::class.java)

    suspend fun getMostPlayedGames() : List<Response> {
        return api.getMostPlayedGames() .await()
    }
}
