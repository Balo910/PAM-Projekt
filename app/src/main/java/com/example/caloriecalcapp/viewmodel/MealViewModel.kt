package com.example.caloriecalcapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecalcapp.data.Meal
import com.example.caloriecalcapp.data.MealDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MealViewModel(private val mealDao: MealDao) : ViewModel() {

    val meals: StateFlow<List<Meal>> = mealDao.getAllMeals()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val totalCalories: StateFlow<Int> = meals
        .map { list -> list.sumOf { it.calories } }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.insertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.deleteMeal(meal)
        }
    }

    private val _dailyLimit = MutableStateFlow(2000)
    val dailyLimit: StateFlow<Int> = _dailyLimit


    fun calculateAndSetDailyLimit(
        weight: Float,
        height: Float,
        age: Int,
        isMale: Boolean
    ) {
        val heightMeters = height / 100
        val bmi = weight / (heightMeters * heightMeters)

        var calories = if (isMale) 2500 else 2000

        calories += when {
            bmi < 18.5 -> 300
            bmi >= 25 -> -300
            else -> 0
        }

        if (age > 50) calories -= 200

        _dailyLimit.value = calories
    }
}
