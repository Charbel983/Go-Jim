package com.example.fitnessapp.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.*
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnessapp.Database.Exercices.ChosenExercise
import com.example.fitnessapp.Database.Workouts.WorkoutDB
import com.example.fitnessapp.R
import java.util.*


class AllDaysAdapter(private var exercises : List<ChosenExercise>, private val context : Context, private val activity : FragmentActivity) : RecyclerView.Adapter<AllDaysAdapter.MondayHolder>() {

    private var animationTime = 400.toLong()

    class MondayHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.chosenExerciseNameTextView)
        val reps : EditText = itemView.findViewById(R.id.chosenExerciseRepsEditText)
        val sets : EditText = itemView.findViewById(R.id.exerciseSetsEditText)
        val deleteButton : ImageView = itemView.findViewById(R.id.chosenExerciseDeleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MondayHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_cardview, parent, false)
        return MondayHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MondayHolder, position: Int) {
        holder.itemView.visibility = View.GONE
        val database = WorkoutDB.get(context).getChosenExerciseDAO()
        val exercise = exercises[position]
        val sharedPrefs = context.getSharedPreferences("chosenWorkout", Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences("days", Context.MODE_PRIVATE)
        val workoutName = sharedPrefs.getString("workoutName", null)
        val day = sharedPreferences.getString("day", workoutName + "Monday")
        holder.name.text = exercise.name
        holder.reps.setText(exercise.reps.toString())
        holder.sets.setText(exercise.sets.toString())
        holder.deleteButton.setOnClickListener {
            database.deleteChosenExercise(exercise.id, workoutName, day!!.substring(workoutName!!.length, day.length))
            exercises = database.getChosenExerciseInDay(workoutName, day.substring(workoutName.length, day.length), exercise.category)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
        holder.name.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.selected_exercise_cardview)
            val closeButton : Button = dialog.findViewById(R.id.chosenExerciseCloseButton)
            val title : TextView = dialog.findViewById(R.id.chosenExerciseNameTextView)
            val image : ImageView = dialog.findViewById(R.id.chosenExerciseImageView)
            val description : TextView = dialog.findViewById(R.id.chosenExerciseDescriptionTextView)
            val sets : EditText = dialog.findViewById(R.id.chosenExerciseSetsEditText)
            val reps : EditText = dialog.findViewById(R.id.chosenExerciseRepsEditText)
            val weightPR : EditText = dialog.findViewById(R.id.chosenExerciseWeightPREditText)
            val repsPR : EditText = dialog.findViewById(R.id.chosenExerciseRepsPREditText)
            description.movementMethod = ScrollingMovementMethod()
            title.text = exercise.name
            description.text = exercise.description
            sets.setText(exercise.sets.toString())
            reps.setText(exercise.reps.toString())
            weightPR.setText(exercise.weightPR)
            repsPR.setText(exercise.repsPR.toString())

            sets.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                        sets.clearFocus()
                        hideSoftKeyboard(activity)
                        return@OnEditorActionListener true
                    }
                }
                false
            })
            sets.setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    val newSets = Integer.parseInt(sets.text.toString())
                    database.updateSets(exercise.name, day!!.substring(workoutName!!.length, day.length), workoutName, newSets)
                    exercises = database.getChosenExerciseInDay(workoutName, day.substring(workoutName.length, day.length), exercise.category)
                    notifyItemChanged(position)
                }
            }

            reps.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                        reps.clearFocus()
                        hideSoftKeyboard(activity)
                        return@OnEditorActionListener true
                    }
                }
                false
            })
            reps.setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    val newReps = Integer.parseInt(reps.text.toString())
                    database.updateReps(exercise.name, day!!.substring(workoutName!!.length, day.length), workoutName, newReps)
                    exercises = database.getChosenExerciseInDay(workoutName, day.substring(workoutName.length, day.length), exercise.category)
                    notifyItemChanged(position)
                }
            }

            weightPR.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                        weightPR.clearFocus()
                        hideSoftKeyboard(activity)
                        return@OnEditorActionListener true
                    }
                }
                false
            })
            weightPR.setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    val newWeight = weightPR.text.toString()
                    database.updateWeightPR(exercise.name, newWeight)
                    exercises = database.getChosenExerciseInDay(workoutName, day!!.substring(workoutName!!.length, day.length), exercise.category)
                    notifyItemChanged(position)
                }
            }

            repsPR.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                        repsPR.clearFocus()
                        hideSoftKeyboard(activity)
                        return@OnEditorActionListener true
                    }
                }
                false
            })
            repsPR.setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    val newReps = Integer.parseInt(repsPR.text.toString())
                    database.updateRepsPR(exercise.name, newReps)
                    exercises = database.getChosenExerciseInDay(workoutName, day!!.substring(workoutName!!.length, day.length), exercise.category)
                    notifyItemChanged(position)
                }
            }

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
        holder.reps.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                    holder.reps.clearFocus()
                    hideSoftKeyboard(activity)
                    return@OnEditorActionListener true
                }
            }
            false
        })
        holder.reps.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                database.updateReps(exercise.name, day!!.substring(workoutName!!.length, day.length), workoutName, Integer.parseInt(holder.reps.text.toString()))
                exercises = database.getChosenExerciseInDay(workoutName, day.substring(workoutName.length, day.length), exercise.category)
                notifyItemChanged(position)
            }
        }
        holder.sets.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS -> {
                    holder.sets.clearFocus()
                    hideSoftKeyboard(activity)
                    return@OnEditorActionListener true
                }
            }
            false
        })
        holder.sets.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                database.updateSets(exercise.name, day!!.substring(workoutName!!.length, day.length), workoutName, Integer.parseInt(holder.sets.text.toString()))
                exercises = database.getChosenExerciseInDay(workoutName, day.substring(workoutName.length, day.length), exercise.category)
                notifyItemChanged(position)
            }
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

    override fun getItemCount(): Int {
        return exercises.size
    }

    private fun hideSoftKeyboard(activity: Activity?) {
        if (activity == null) return
        if (activity.currentFocus == null) return
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }
}