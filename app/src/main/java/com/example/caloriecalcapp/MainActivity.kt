package com.example.caloriecalcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.caloriecalcapp.navigation.NavGraph
import com.example.caloriecalcapp.ui.theme.CalorieCalcAppTheme
import androidx.activity.viewModels
import com.example.caloriecalcapp.viewmodel.MealViewModel
import com.example.caloriecalcapp.viewmodel.MealViewModelFactory


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mealDao =
            com.example.caloriecalcapp.data.AppDatabase.getDatabase(applicationContext).mealDao()
        val factory = MealViewModelFactory(mealDao)
        val mealViewModel: MealViewModel by viewModels { factory }

        setContent {
            CalorieCalcAppTheme(
                darkTheme = true,
                dynamicColor = false
            ) {
                NavGraph(mealViewModel = mealViewModel)
            }
        }
    }
}
