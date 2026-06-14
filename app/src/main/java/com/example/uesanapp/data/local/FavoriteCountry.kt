package com.example.uesanapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_countries")
data class FavoriteCountry(
    @PrimaryKey val name: String,
    val code: String?,
    val flag: String?
)
