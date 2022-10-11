package com.example.happyplace.Activity.Models

import java.io.Serializable

data class HappyPlaceModel(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val location: String,
    val longitude: Double,
    val latitude: Double,
    val date: String
): Serializable
