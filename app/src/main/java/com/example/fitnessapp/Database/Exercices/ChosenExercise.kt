package com.example.fitnessapp.Database.Exercices

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "chosenExercises")
data class ChosenExercise(
    @PrimaryKey
    @ColumnInfo
    val id : String,

    @ColumnInfo
    val name : String,

    @ColumnInfo
    val image : Int,

    @ColumnInfo
    val category : String,

    @ColumnInfo
    val reps : Int,

    @ColumnInfo
    val weightPR : String,

    @ColumnInfo
    val repsPR : Int,

    @ColumnInfo
    val sets : Int,

    @ColumnInfo
    val day : String,

    @ColumnInfo
    val description : String,

    @ColumnInfo
    val workoutName : String
)