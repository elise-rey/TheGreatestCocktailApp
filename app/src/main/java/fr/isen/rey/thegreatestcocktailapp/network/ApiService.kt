package fr.isen.rey.thegreatestcocktailapp.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("random.php")
    fun getRandomCocktail(): Call<Drinks>

    @GET("list.php?c=list")
    fun getCategoryList(): Call<Drinks>
}