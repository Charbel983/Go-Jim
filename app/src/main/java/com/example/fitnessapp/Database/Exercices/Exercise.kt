package com.example.fitnessapp.Database.Exercices

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "allExercises")
data class Exercise(
    @PrimaryKey
    @ColumnInfo
    val id : Int,

    @ColumnInfo
    val name : String,

    @ColumnInfo
    val category : String,

    @ColumnInfo
    val description : String,

    @ColumnInfo
    val image : Int
)