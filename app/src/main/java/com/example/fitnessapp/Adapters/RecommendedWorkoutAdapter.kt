package com.example.fitnessapp.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.Activities.WorkoutActivity
import com.example.fitnessapp.Database.Workouts.Workout
import com.example.fitnessapp.R

class RecommendedWorkoutAdapter (private var workoutList : List<Workout>?, private val context : Context) : RecyclerView.Adapter<RecommendedWorkoutAdapter.WorkoutHolder>() {

    class WorkoutHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recommendedWorkoutTitle)
        val image : ImageView = itemView.findViewById(R.id.recommendedWorkoutCardImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_workout_cardview, parent, false)
        return WorkoutHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
        val sharedPrefs = context.getSharedPreferences("chosenWorkout", Context.MODE_PRIVATE)
        val workout = workoutList?.get(position)
        holder.title.text = workout?.workoutName
        holder.image.setImageDrawable(context.getDrawable(workout?.image!!))
        holder.title.setOnClickListener {
            sharedPrefs.edit().putString("workoutName", workout.workoutName).apply()
            context.startActivity(Intent(context, WorkoutActivity::class.java))
        }
        holder.image.setOnClickListener {
            sharedPrefs.edit().putString("workoutName", workout.workoutName).apply()
            context.startActivity(Intent(context, WorkoutActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return workoutList?.size!!
    }
}