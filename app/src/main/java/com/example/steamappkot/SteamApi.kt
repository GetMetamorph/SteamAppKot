package com.example.steamappkot

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type

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
    val peak_in_game: Int,
    @SerializedName("detailed_description")
    val detailed_description: String,
)

data class SteamAppResponse(
    val game: SteamGameResponse
    )

data class SteamGameResponse(
    val success: Boolean,
    val data: SteamGameResponseData?
)

data class SteamGameResponseData(
    val name: String,
    val price: String,
    val publishers: ArrayList<String>,
    val is_free: Boolean,
    val header_image: String,
    val background: String,
    val price_overview: PriceOverview
)

data class PriceOverview(
    val currency: String,
    val final_formatted: String
)

interface SteamAPI {

    //In get add the follow of the url ex: https://https://api.steampowered.com/ become https://https://api.steampowered.com/GetMostPlayedGames/
    @GET("ISteamChartsService/GetMostPlayedGames/v1/?")
    fun getMostPlayedGames() : Deferred<SteamResponse>

    @GET("appdetails")
    fun getAppDetail(@Query("appids") id: String): Deferred<SteamAppResponse>
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


    private val store = Retrofit.Builder()
        .baseUrl("https://store.steampowered.com/api/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .registerTypeAdapter(SteamAppResponse::class.java, SteamResponseDeserializer())
                    .create()
            ))
        .addCallAdapterFactory(
            CoroutineCallAdapterFactory()
        )
        .client(okHttp)
        .build()
        .create(SteamAPI::class.java)

    suspend fun getMostPlayedGames(): List<Rank> {
        return api.getMostPlayedGames().await().response.ranks.take(5)
    }

    suspend fun getAppDetail(appid: String): SteamAppResponse {
        return store.getAppDetail(appid).await()
    }
}

class SteamResponseDeserializer : JsonDeserializer<SteamAppResponse> {

    companion object {
        val deserializer: Gson = GsonBuilder().create()
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SteamAppResponse {
        // On récupère le JSON
        val jsonObject = json?.asJsonObject
        val key = jsonObject?.keySet()?.first { it.toIntOrNull() != null }

        return SteamAppResponse(
            deserializer.fromJson(
                jsonObject?.get(key), SteamGameResponse::class.java
            )
        )
    }
}