package com.example.steamappkot

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class SteamResponse(
    @SerializedName("response")
    val response: Response
)

data class Response(
    @SerializedName("rollup_date")
    val rollup_date: Int,
    @SerializedName("ranks")
    val ranks: List<Rank>
)


data class Rank (
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("appid")
    val appid: Int,
    @SerializedName("last_week_rank")
    val last_week_rank: Int,
    @SerializedName("peak_in_game")
    val peak_in_game: Int
)
val apiKey: String = System.getenv("STEAM_API_KEY")

interface SteamAPI {

    //In get add the follow of the url ex: https://https://api.steampowered.com/ become https://https://api.steampowered.com/GetMostPlayedGames/
    @GET("ISteamChartsService/GetMostPlayedGames/v1/?")
    fun getMostPlayedGames() : Deferred<SteamResponse>

    //https://api.steampowered.com/ISteamChartsService/GetMostPlayedGames/v1/?
}

object CallAPI {
    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .build()

    private val api = Retrofit.Builder()
        .baseUrl("https://api.steampowered.com/")
        .addConverterFactory(
            GsonConverterFactory.create())
        .addCallAdapterFactory(
            CoroutineCallAdapterFactory()
        )
        .client(okHttp)
        .build()
        .create(SteamAPI::class.java)

    suspend fun getMostPlayedGames() : SteamResponse {
        return api.getMostPlayedGames().await()
    }
}