package com.example.fitnessapp.Database.Workouts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkoutDAO {

    @Query("delete from workout where workoutName = :name ")
    fun deleteWorkout(name : String?)

    @Query("select exists(select * from workout where workoutName = :name)")
    fun workoutAlreadyExists(name : String) : Boolean

    @Query("select * from workout")
    fun getWorkouts() : List<Workout>

    @Insert
    fun addWorkout(vararg workout : Workout)
}