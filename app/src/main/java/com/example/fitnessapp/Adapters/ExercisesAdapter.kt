package com.example.fitnessapp.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.method.ScrollingMovementMethod
import android.view.*
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnessapp.Database.Exercices.ChosenExercise
import com.example.fitnessapp.Database.Exercices.Exercise
import com.example.fitnessapp.Database.Workouts.WorkoutDB
import com.example.fitnessapp.R
import java.util.*

class ExercisesAdapter(private var exercisesList : List<Exercise>, private val context : Context, private val activity: Activity) : RecyclerView.Adapter<ExercisesAdapter.PushExerciseHolder>()  {

    private var animationTime = 200.toLong()

    class PushExerciseHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.chosenExerciseNameTextView)
        val selectButton : ImageView = itemView.findViewById(R.id.chosenExerciseDeleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PushExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_exercise_cardview, parent, false)
        return PushExerciseHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: PushExerciseHolder, position: Int) {
        holder.itemView.visibility = View.GONE
        val exercise = exercisesList[position]
        holder.name.text = exercise.name
        val add = context.resources.getIdentifier("@drawable/fab_add", null, context.packageName)
        val remove = context.resources.getIdentifier("@android:drawable/ic_menu_close_clear_cancel", null, context.packageName)
        val database = WorkoutDB.get(context).getChosenExerciseDAO()
        val sharedPreferences = context.getSharedPreferences("days", Context.MODE_PRIVATE)
        val sharedPrefs = context.getSharedPreferences("chosenWorkout", Context.MODE_PRIVATE)
        val workoutName = sharedPrefs.getString("workoutName", null)
        val day = sharedPreferences.getString("day", workoutName + "Monday")

        if(database.chosenExerciseExists(workoutName!!, day + exercise.id, day!!.substring(workoutName.length, day.length))){
            val res = context.resources.getDrawable(remove, null)
            holder.selectButton.setBackgroundColor(Color.parseColor("#B7FF0000"))
            holder.selectButton.setImageDrawable(res)
        }else{
            val res = context.resources.getDrawable(add, null)
            holder.selectButton.setBackgroundColor(Color.parseColor("#D58BC34A"))
            holder.selectButton.setImageDrawable(res)
        }

        holder.selectButton.setOnClickListener {
            if(database.chosenExerciseExists(workoutName, day + exercise.id, day.substring(workoutName.length, day.length))){
                val res = context.resources.getDrawable(add, null)
                holder.selectButton.setBackgroundColor(Color.parseColor("#D58BC34A"))
                holder.selectButton.setImageDrawable(res)
                database.deleteChosenExercise(day + exercise.id, workoutName, day.substring(workoutName.length, day.length))
            }else{
                val res = context.resources.getDrawable(remove, null)
                holder.selectButton.setBackgroundColor(Color.parseColor("#B7FF0000"))
                holder.selectButton.setImageDrawable(res)
                var weightPR = ""
                var repsPR = 0
                if(database.exerciseExists(exercise.name)){
                    val e = database.getExercise(exercise.name)
                    weightPR = e[0].weightPR
                    repsPR = e[0].repsPR
                }
                val chosenExercise = ChosenExercise(day + exercise.id, exercise.name, exercise.image, exercise.category, 0, weightPR, repsPR, 4, day.substring(workoutName.length, day.length), exercise.description, workoutName)
                database.addChosenExercise(chosenExercise)
            }
        }
        holder.name.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.description_exercise)
            val closeButton : Button = dialog.findViewById(R.id.closeDescriptionButton)
            val title : TextView = dialog.findViewById(R.id.descriptionExerciseNameTextView)
            val image : ImageView = dialog.findViewById(R.id.descriptionExerciseImageView)
            val description : TextView = dialog.findViewById(R.id.descriptionExerciseTextView)
            description.movementMethod = ScrollingMovementMethod()
            title.text = exercise.name
            description.text = exercise.description
            val res = context.resources.getDrawable(exercise.image, null)
            Glide.with(context).load(res).into(image)
            closeButton.setOnClickListener {
                dialog.cancel()
            }
            dialog.show()
            val window = dialog.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            window?.setGravity(Gravity.CENTER)
        }
        Timer().schedule(object : TimerTask(){
            override fun run() {
                activity.runOnUiThread {
                    holder.itemView.visibility = View.VISIBLE
                }
                setScaleAnimation(holder.itemView)
            }
        }, animationTime)
        animationTime += 100
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = 500
        view.startAnimation(anim)
    }
}