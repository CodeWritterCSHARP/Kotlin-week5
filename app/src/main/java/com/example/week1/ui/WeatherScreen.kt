package com.example.week1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week1.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sää nyt",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = uiState.city,
            onValueChange = { viewModel.updateCity(it) },
            label = { Text("Kaupunki") },
            placeholder = { Text("esim. Helsinki") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.fetchWeather() },
            enabled = !uiState.isLoading && uiState.city.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hae sää")
        }

        Spacer(modifier = Modifier.height(32.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Ladataan säätietoja...")
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error ?: "Tuntematon virhe",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            uiState.weather != null -> {
                WeatherResultSection(weather = uiState.weather!!)
            }

            else -> {
                Text(
                    text = "Syötä kaupungin nimi ja paina \"Hae sää\"",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}