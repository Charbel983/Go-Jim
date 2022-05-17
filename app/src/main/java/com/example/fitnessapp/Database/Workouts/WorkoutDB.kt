package com.example.fitnessapp.Database.Workouts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fitnessapp.Database.Exercices.ChosenExercise
import com.example.fitnessapp.Database.Exercices.ChosenExerciseDAO


@Database(entities = [Workout::class, ChosenExercise::class], version = 1)
abstract class WorkoutDB : RoomDatabase(){
    abstract fun getWorkoutDAO() : WorkoutDAO
    abstract fun getChosenExerciseDAO() : ChosenExerciseDAO

    companion object {
        fun get(context: Context) : WorkoutDB {
            return buildDatabase(context)
        }
        private fun buildDatabase(context: Context) : WorkoutDB {
            return Room.databaseBuilder(context, WorkoutDB::class.java, "workoutDatabase")
                .allowMainThreadQueries().build()
        }
    }
}