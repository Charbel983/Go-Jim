package com.example.fitnessapp.Database.Workouts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workout")
data class Workout (

    @PrimaryKey
    @ColumnInfo(name = "workoutName")
    val workoutName : String,

    @ColumnInfo(name = "createdDate")
    val createdDate : String,

    @ColumnInfo(name = "image")
    val image : Int
)