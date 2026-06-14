package com.example.uesanapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("SELECT * FROM favorite_countries")
    fun getAllFavorites(): Flow<List<FavoriteCountry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(country: FavoriteCountry)

    @Delete
    suspend fun deleteFavorite(country: FavoriteCountry)

    @Query("SELECT EXISTS(SELECT * FROM favorite_countries WHERE name = :countryName)")
    suspend fun isFavorite(countryName: String): Boolean
}
