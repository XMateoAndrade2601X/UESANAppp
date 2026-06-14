package com.example.uesanapp.data.repository

import com.example.uesanapp.data.local.CountryDao
import com.example.uesanapp.data.local.FavoriteCountry
import com.example.uesanapp.data.remote.apifootball.ApiFootballService
import com.example.uesanapp.data.remote.apifootball.Country
import kotlinx.coroutines.flow.Flow

class CountryRepository(
    private val apiService: ApiFootballService,
    private val countryDao: CountryDao
) {
    suspend fun getCountriesFromApi(): List<Country> {
        return apiService.getCountries().response
    }

    fun getFavoriteCountries(): Flow<List<FavoriteCountry>> {
        return countryDao.getAllFavorites()
    }

    suspend fun addFavorite(country: FavoriteCountry) {
        countryDao.insertFavorite(country)
    }

    suspend fun removeFavorite(country: FavoriteCountry) {
        countryDao.deleteFavorite(country)
    }

    suspend fun isFavorite(countryName: String): Boolean {
        return countryDao.isFavorite(countryName)
    }
}
