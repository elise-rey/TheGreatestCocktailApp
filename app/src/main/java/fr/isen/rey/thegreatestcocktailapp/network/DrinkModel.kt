package fr.isen.rey.thegreatestcocktailapp.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Drinks(
    val drinks: List<DrinkModel>
): Serializable

class DrinkModel(
    @SerializedName("idDrink") val id: String = "",
    @SerializedName("strDrink") val name: String = ""
): Serializable