package com.example.fitnessapp.Controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fitnessapp.Activities.LegsAbsExercisesActivity
import com.example.fitnessapp.Activities.PullExercisesActivity
import com.example.fitnessapp.Activities.PushExercisesActivity
import com.example.fitnessapp.Adapters.AllDaysAdapter
import com.example.fitnessapp.Adapters.RecommendedAllDaysAdapter
import com.example.fitnessapp.Database.Exercices.ChosenExercise
import com.example.fitnessapp.Database.Workouts.WorkoutDB
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu

class AllDaysFragmentController(private val selectedDay : String, private val addExercisesButton : FloatingActionMenu, private val recyclerView: RecyclerView,
                                private val spinner: Spinner, private val swipeRefreshLayout: SwipeRefreshLayout, private val activity : FragmentActivity, private val context : Context) {

    private var autoLogin = 0

    class SpinnerActivity(private val recyclerView: RecyclerView,
                          private val chestExercises : List<ChosenExercise>,
                          private val tricepsExercises : List<ChosenExercise>,
                          private val shouldersExercises : List<ChosenExercise>,
                          private val backExercises : List<ChosenExercise>,
                          private val bicepsExercises : List<ChosenExercise>,
                          private val forearmsExercises : List<ChosenExercise>,
                          private val legsExercises : List<ChosenExercise>,
                          private val absExercises : List<ChosenExercise>,
                          private val activity: FragmentActivity,
                          private val autoLogin : Int) : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            when {
                parent.getItemAtPosition(pos).equals("Chest") -> showData(chestExercises)
                parent.getItemAtPosition(pos).equals("Triceps") -> showData(tricepsExercises)
                parent.getItemAtPosition(pos).equals("Shoulders") -> showData(shouldersExercises)
                parent.getItemAtPosition(pos).equals("Back") -> showData(backExercises)
                parent.getItemAtPosition(pos).equals("Biceps") -> showData(bicepsExercises)
                parent.getItemAtPosition(pos).equals("Forearms") -> showData(forearmsExercises)
                parent.getItemAtPosition(pos).equals("Legs") -> showData(legsExercises)
                else -> showData(absExercises)
            }
        }
        private fun showData(exercises : List<ChosenExercise>) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = if(autoLogin == 2){
                    RecommendedAllDaysAdapter(exercises, context, activity)
                }else{
                    AllDaysAdapter(exercises, context, activity)
                }
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>) {
        }
    }

    fun start(){
        val sharedPreferences = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
        autoLogin = sharedPreferences.getInt("key", 1)
        if(autoLogin == 2){
            addExercisesButton.visibility = View.GONE
        }
        val sharedPrefs = context.getSharedPreferences("chosenWorkout", Context.MODE_PRIVATE)
        val workoutName = sharedPrefs.getString("workoutName", null)
        val database = WorkoutDB.get(context).getChosenExerciseDAO()
        val chestList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Chest"), workoutName!!)
        val tricepsList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Triceps"), workoutName)
        val shouldersList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Shoulders"), workoutName)
        val backList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Back"), workoutName)
        val bicepsList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Biceps"), workoutName)
        val forearmsList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Forearms"), workoutName)
        val legsList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Legs"), workoutName)
        val absList = sortList(database.getChosenExerciseInDay(workoutName, selectedDay, "Abs"), workoutName)
        val color = context.getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val spinnerArray = ArrayList<String>()
        recyclerView.scheduleLayoutAnimation()
        var allListsEmpty = true
        if(chestList.isNotEmpty()){
            spinnerArray.add("Chest")
            allListsEmpty = false
        }
        if(tricepsList.isNotEmpty()){
            spinnerArray.add("Triceps")
            allListsEmpty = false
        }
        if(shouldersList.isNotEmpty()){
            spinnerArray.add("Shoulders")
            allListsEmpty = false
        }
        if(backList.isNotEmpty()){
            spinnerArray.add("Back")
            allListsEmpty = false
        }
        if(bicepsList.isNotEmpty()){
            spinnerArray.add("Biceps")
            allListsEmpty = false
        }
        if(forearmsList.isNotEmpty()){
            spinnerArray.add("Forearms")
            allListsEmpty = false
        }
        if(legsList.isNotEmpty()){
            spinnerArray.add("Legs")
            allListsEmpty = false
        }
        if(absList.isNotEmpty()){
            spinnerArray.add("Abs")
            allListsEmpty = false
        }
        if(allListsEmpty){
            spinnerArray.add("Please Add Exercises With The Add Button Below")
        }
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        swipeRefreshLayout.setOnRefreshListener {
            when(spinner.selectedItem.toString()) {
                "Triceps" -> showData(tricepsList)
                "Shoulders" -> showData(shouldersList)
                "Back" -> showData(backList)
                "Biceps" -> showData(bicepsList)
                "Forearms" -> showData(forearmsList)
                "Legs" -> showData(legsList)
                "Abs" -> showData(absList)
                else -> showData(chestList)
            }
            swipeRefreshLayout.isRefreshing = false
        }
        swipeRefreshLayout.setColorSchemeColors(color.getInt("color", Color.BLUE))

        when {
            chestList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Chest", spinnerArray))
            tricepsList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Triceps", spinnerArray))
            shouldersList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Shoulders", spinnerArray))
            backList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Back", spinnerArray))
            bicepsList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Biceps", spinnerArray))
            forearmsList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Forearms", spinnerArray))
            legsList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Legs", spinnerArray))
            absList.isNotEmpty() -> spinner.setSelection(findItemInSpinner("Abs", spinnerArray))
        }

        spinner.onItemSelectedListener = SpinnerActivity(recyclerView, chestList, tricepsList, shouldersList, backList, bicepsList, forearmsList, legsList, absList, activity, autoLogin)

        val chestButton = addExercisesButton[0] as FloatingActionButton
        val backButton  = addExercisesButton[1] as FloatingActionButton
        val legsButton  = addExercisesButton[2] as FloatingActionButton
        chestButton.colorNormal = color.getInt("color", Color.BLUE)
        chestButton.labelText = "Add Push Exercises"
        backButton.colorNormal = color.getInt("color", Color.BLUE)
        backButton.labelText = "Add Pull Exercises"
        legsButton.colorNormal = color.getInt("color", Color.BLUE)
        legsButton.labelText = "Add Legs/Abs Exercises"

        addExercisesButton.menuButtonColorNormal = color.getInt("color", Color.BLUE)
        addExercisesButton.menuButtonColorPressed = color.getInt("color", Color.BLUE)

        chestButton.setOnClickListener {
            context.startActivity(Intent(context, PushExercisesActivity::class.java))
        }
        backButton.setOnClickListener {
            context.startActivity(Intent(context, PullExercisesActivity::class.java))
        }
        legsButton.setOnClickListener {
            context.startActivity(Intent(context, LegsAbsExercisesActivity::class.java))
        }
    }

    private fun findItemInSpinner(name : String, array : ArrayList<String>) : Int{
        for(i in array.indices){
            if(name == array[i]){
                return i
            }
        }
        return 0
    }

    private fun showData(exercises : List<ChosenExercise>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = if(autoLogin == 2){
                RecommendedAllDaysAdapter(exercises, context, activity)
            }else{
                AllDaysAdapter(exercises, context, activity)
            }
        }
    }

    private fun sortList(allExercises : List<ChosenExercise>, workoutName : String) : List<ChosenExercise> {
        val list = ArrayList<ChosenExercise>()
        for(i in allExercises.indices){
            if(allExercises[i].workoutName == workoutName && allExercises[i].day == selectedDay){
                list.add(allExercises[i])
            }
        }
        return list
    }
}