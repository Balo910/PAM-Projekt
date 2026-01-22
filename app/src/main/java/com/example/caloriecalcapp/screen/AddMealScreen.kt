package com.example.caloriecalcapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.caloriecalcapp.data.Meal
import com.example.caloriecalcapp.R
import com.example.caloriecalcapp.viewmodel.MealViewModel

@Composable
fun AddMealScreen(
    viewModel: MealViewModel,
    onMealSaved: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    val addMealText = stringResource(R.string.add_meal_button)
    val mealNameLabel = stringResource(R.string.meal_name_label)
    val caloriesLabel = stringResource(R.string.calories_label)
    val saveButtonText = stringResource(R.string.add_meal_button)
    val backButtonText = stringResource(R.string.cancel_button)
    val mealSavedToast = stringResource(R.string.meal_saved_toast)
    val invalidDataToast = stringResource(R.string.invalid_data_toast)

    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = addMealText,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(mealNameLabel) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )

        OutlinedTextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text(caloriesLabel) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.End)
        ) {
            Button(onClick = onBackClick) {
                Text(backButtonText)
            }

            Button(onClick = {
                val caloriesInt = calories.toIntOrNull()
                if (name.isNotBlank() && caloriesInt != null) {
                    viewModel.addMeal(Meal(name = name, calories = caloriesInt))
                    Toast.makeText(context, mealSavedToast, Toast.LENGTH_SHORT).show()
                    onMealSaved()
                } else {
                    Toast.makeText(context, invalidDataToast, Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(saveButtonText)
            }
        }
    }
}