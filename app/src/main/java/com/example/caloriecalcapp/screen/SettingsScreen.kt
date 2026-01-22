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
import com.example.caloriecalcapp.R
import com.example.caloriecalcapp.viewmodel.MealViewModel

@Composable
fun SettingsScreen(
    viewModel: MealViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val dailyLimit by viewModel.dailyLimit.collectAsState()

    val settingsTitle = stringResource(R.string.settings_title)
    val currentLimitText = stringResource(R.string.current_limit)
    val weightLabel = stringResource(R.string.weight_label)
    val heightLabel = stringResource(R.string.height_label)
    val ageLabel = stringResource(R.string.age_label)
    val maleLabel = stringResource(R.string.male_label)
    val femaleLabel = stringResource(R.string.female_label)
    val calculateButtonText = stringResource(R.string.calculate_limit_button)
    val backButtonText = stringResource(R.string.cancel_button)
    val limitCalculatedToast = stringResource(R.string.limit_calculated)
    val invalidDataToast = stringResource(R.string.invalid_data)
    val invalidWeightToast = stringResource(R.string.invalid_weight)
    val invalidHeightToast = stringResource(R.string.invalid_height)
    val invalidAgeToast = stringResource(R.string.invalid_age)

    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = settingsTitle,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "$currentLimitText $dailyLimit kcal",
            color = MaterialTheme.colorScheme.onBackground
        )

        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text(weightLabel) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text(heightLabel) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(ageLabel) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = isMale,
                onClick = { isMale = true }
            )
            Text(maleLabel, color = MaterialTheme.colorScheme.onBackground)

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = !isMale,
                onClick = { isMale = false }
            )
            Text(femaleLabel, color = MaterialTheme.colorScheme.onBackground)
        }

        Button(
            onClick = {
                val w = weight.toFloatOrNull()
                val h = height.toFloatOrNull()
                val a = age.toIntOrNull()

                if (w == null || h == null || a == null) {
                    Toast.makeText(context, invalidDataToast, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (w !in 20f..500f) {
                    Toast.makeText(context, invalidWeightToast, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (h !in 50f..250f) {
                    Toast.makeText(context, invalidHeightToast, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (a !in 1..120) {
                    Toast.makeText(context, invalidAgeToast, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                viewModel.calculateAndSetDailyLimit(
                    weight = w,
                    height = h,
                    age = a,
                    isMale = isMale
                )
                Toast.makeText(context, limitCalculatedToast, Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(calculateButtonText)
        }

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(backButtonText)
        }
    }
}
