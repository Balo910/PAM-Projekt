package com.example.caloriecalcapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caloriecalcapp.screen.AddMealScreen
import com.example.caloriecalcapp.screen.MealListScreen
import com.example.caloriecalcapp.screen.SettingsScreen
import com.example.caloriecalcapp.viewmodel.MealViewModel
import com.example.caloriecalcapp.ui.theme.CalorieCalcAppTheme

sealed class Screen(val route: String) {
    object MealList : Screen("meal_list")
    object AddMeal : Screen("add_meal")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    mealViewModel: MealViewModel,
    navController: NavHostController = rememberNavController()
) {

    CalorieCalcAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = Screen.MealList.route) {

                composable(Screen.MealList.route) {
                    MealListScreen(
                        viewModel = mealViewModel,
                        onAddMealClick = { navController.navigate(Screen.AddMeal.route) },
                        onSettingsClick = { navController.navigate(Screen.Settings.route) }
                    )
                }

                composable(Screen.AddMeal.route) {
                    AddMealScreen(
                        viewModel = mealViewModel,
                        onMealSaved = { navController.popBackStack() },
                        onBackClick = { navController.popBackStack() }
                    )
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(
                        viewModel = mealViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}