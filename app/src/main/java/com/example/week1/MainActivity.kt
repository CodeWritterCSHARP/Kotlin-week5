package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.week1.ui.theme.Week1Theme

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week1.ui.WeatherScreen
import com.example.week1.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week1Theme {
                val weatherViewModel: WeatherViewModel = viewModel()
                WeatherScreen(weatherViewModel)
            }
        }
    }
}