package com.example.uesanapp.presentation.countries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.uesanapp.data.local.AppDatabase
import com.example.uesanapp.data.local.FavoriteCountry
import com.example.uesanapp.data.remote.apifootball.Country
import com.example.uesanapp.data.remote.apifootball.RetrofitInstance
import com.example.uesanapp.data.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CountryRepository

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries.asStateFlow()

    private val _favorites = MutableStateFlow<List<FavoriteCountry>>(emptyList())
    val favorites: StateFlow<List<FavoriteCountry>> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = CountryRepository(RetrofitInstance.api, database.countryDao())
        
        loadCountries()
        observeFavorites()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _countries.value = repository.getCountriesFromApi()
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getFavoriteCountries().collectLatest {
                _favorites.value = it
            }
        }
    }

    fun toggleFavorite(country: Country) {
        viewModelScope.launch {
            val favorite = FavoriteCountry(country.name, country.code, country.flag)
            if (_favorites.value.any { it.name == country.name }) {
                repository.removeFavorite(favorite)
            } else {
                repository.addFavorite(favorite)
            }
        }
    }
}
