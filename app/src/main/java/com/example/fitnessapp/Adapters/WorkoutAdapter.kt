package com.example.fitnessapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.Activities.WorkoutActivity
import com.example.fitnessapp.Database.Workouts.Workout
import com.example.fitnessapp.Database.Workouts.WorkoutDB
import com.example.fitnessapp.R

class WorkoutAdapter (private var workoutList : List<Workout>?, private val context : Context) : RecyclerView.Adapter<WorkoutAdapter.WorkoutHolder>() {

    class WorkoutHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.workoutTitle)
        val date : TextView = itemView.findViewById(R.id.createdDate)
        val pressButton : ImageView = itemView.findViewById(R.id.workoutPressButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_cardview, parent, false)
        return WorkoutHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
        val database = WorkoutDB.get(context).getWorkoutDAO()
        val exercises = WorkoutDB.get(context).getChosenExerciseDAO()
        val sharedPrefs = context.getSharedPreferences("chosenWorkout", Context.MODE_PRIVATE)
        val workout = workoutList?.get(position)
        holder.title.text = workout?.workoutName
        holder.date.text = workout?.createdDate
        holder.deleteButton.setOnClickListener {
            database.deleteWorkout(workout?.workoutName)
            exercises.deleteExercisesFromWorkout(workout?.workoutName)
            workoutList = database.getWorkouts()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
        holder.pressButton.setOnClickListener {
            sharedPrefs.edit().putString("workoutName", workout?.workoutName).apply()
            context.startActivity(Intent(context, WorkoutActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return workoutList?.size!!
    }
}