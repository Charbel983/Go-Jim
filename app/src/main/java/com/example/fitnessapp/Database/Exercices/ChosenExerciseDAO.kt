package com.example.fitnessapp.Database.Exercices

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ChosenExerciseDAO {
    @Query("select * from chosenExercises where workoutName = :workoutName and day = :day and category =:category")
    fun getChosenExerciseInDay(workoutName : String?, day : String, category : String) : List<ChosenExercise>

    @Query("select * from chosenExercises where name = :name")
    fun getExercise(name : String?) : List<ChosenExercise>

    @Query("select exists(select name from chosenExercises where name = :name)")
    fun exerciseExists(name : String?) : Boolean

    @Query("select reps from chosenExercises where id = :id and workoutName = :workoutName and day = :day")
    fun getReps(id: String?, workoutName: String?, day: String) : Int

    @Query("select sets from chosenExercises where id = :id and workoutName = :workoutName and day = :day")
    fun getSets(id: String?, workoutName: String?, day: String) : Int

    @Query("delete from chosenExercises where workoutName = :workoutName")
    fun deleteExercisesFromWorkout(workoutName: String?)

    @Query("delete from chosenExercises where id = :id and workoutName = :workoutName and day = :day")
    fun deleteChosenExercise(id : String?, workoutName: String?, day: String)

    @Query("select exists(select id from chosenExercises where workoutName = :workoutName and id = :id and day = :day)")
    fun chosenExerciseExists(workoutName: String, id : String, day: String) : Boolean

    @Query("update chosenExercises set reps = :reps where name = :name and workoutName = :workoutName and day = :day")
    fun updateReps(name : String?, day: String, workoutName: String?, reps : Int)

    @Query("update chosenExercises set sets = :sets where name = :name and workoutName = :workoutName and day = :day")
    fun updateSets(name : String?, day : String, workoutName: String?, sets : Int)

    @Query("update chosenExercises set weightPR = :weight where name = :name")
    fun updateWeightPR(name : String?, weight : String)

    @Query("update chosenExercises set repsPR = :reps where name = :name")
    fun updateRepsPR(name : String?, reps: Int)

    @Insert
    fun addChosenExercise(vararg exercise: ChosenExercise)

}