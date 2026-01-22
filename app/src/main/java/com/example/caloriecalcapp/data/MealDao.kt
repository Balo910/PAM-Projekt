package com.example.caloriecalcapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(meal: Meal)

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<Meal>>

    @Delete
    suspend fun deleteMeal(meal: Meal)
}
