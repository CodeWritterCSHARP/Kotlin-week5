package com.example.week1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week1.BuildConfig
import com.example.week1.data.remote.RetrofitInstance
import com.example.week1.data.model.WeatherResponse
import kotlinx.coroutines.launch
import android.util.Log

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val error: String? = null,
    val city: String = ""
)

class WeatherViewModel : ViewModel() {

    var uiState by mutableStateOf(WeatherUiState())
        private set

    init {
        Log.d("WeatherViewModel", "API Key: ${BuildConfig.OPENWEATHER_API_KEY}")
    }

    fun updateCity(newCity: String) {
        uiState = uiState.copy(city = newCity.trim())
    }

    fun fetchWeather() {
        if (uiState.city.isBlank()) {
            uiState = uiState.copy(error = "Syötä kaupungin nimi")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            try {
                val response = RetrofitInstance.api.getCurrentWeather(
                    city = uiState.city,
                    apiKey = BuildConfig.OPENWEATHER_API_KEY
                )
                uiState = uiState.copy(
                    isLoading = false,
                    weather = response,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Virhe säätietojen haussa"
                )
            }
        }
    }
}