package com.example.caloriecalcapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.caloriecalcapp.R
import com.example.caloriecalcapp.ui.theme.Red1
import com.example.caloriecalcapp.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealListScreen(
    viewModel: MealViewModel,
    onAddMealClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    val meals by viewModel.meals.collectAsState()
    val totalCalories by viewModel.totalCalories.collectAsState()
    val dailyLimit by viewModel.dailyLimit.collectAsState()
    val titleText = stringResource(R.string.meal_list_title)
    val addingMealToast = stringResource(R.string.adding_meal_toast)
    val noMealsText = stringResource(R.string.no_meals)
    val deleteButtonText = stringResource(R.string.delete_button)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titleText) },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, addingMealToast, Toast.LENGTH_SHORT).show()
                    onAddMealClick()
                },
                containerColor = Red1
            ) {
                Text("+", color = Color.White)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = stringResource(R.string.total_calories, totalCalories, dailyLimit),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )


            Text(
                text = "Liczba posiłków: ${meals.size}",
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            val progress = (totalCalories.toFloat() / dailyLimit).coerceIn(0f, 1f)
            val progressColor = if (progress >= 1f) Color.Green else Color(0xFFBCBCBC)

            LinearProgressIndicator(
                progress = progress,
                color = progressColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (meals.isEmpty()) {
                Text(
                    text = noMealsText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(meals) { meal ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(meal.name, color = MaterialTheme.colorScheme.onBackground)
                                Text("${meal.calories} kcal", color = MaterialTheme.colorScheme.onBackground)
                            }

                            Button(
                                onClick = { viewModel.deleteMeal(meal) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(deleteButtonText, color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }
            }
        }
    }
}
