package com.example.fitnessapp.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.Adapters.WorkoutAdapter
import com.example.fitnessapp.Database.Workouts.Workout
import com.example.fitnessapp.Database.Workouts.WorkoutDB
import com.example.fitnessapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*



/**
 * A simple [Fragment] subclass.
 * Use the [YourWorkoutsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YourWorkoutsFragment : Fragment() {

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var parentHolder : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        parentHolder = inflater.inflate(R.layout.fragment_your_workouts, container, false)
        val database = WorkoutDB.get(requireContext()).getWorkoutDAO()
        val addWorkoutButton : FloatingActionButton = parentHolder.findViewById(R.id.addWorkoutButton)
        val recyclerView : RecyclerView = parentHolder.findViewById(R.id.workoutRecyclerView)
        val title : TextView = parentHolder.findViewById(R.id.workoutsTabTitle)
        val color = requireContext().getSharedPreferences("Theme", Context.MODE_PRIVATE)
        title.setTextColor(color.getInt("color", Color.BLUE))
        addWorkoutButton.backgroundTintList = ColorStateList.valueOf(color.getInt("color", Color.BLUE))
        addWorkoutButton.imageTintList = ColorStateList.valueOf(Color.WHITE)

        var workouts = database.getWorkouts()

        addWorkoutButton.setOnClickListener {
            val editText = EditText(context)
            editText.isSingleLine = true
            var name : String
            editText.hint = "Workout Name"
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Add Workout")
            alertDialog.setView(editText)
            alertDialog.setPositiveButton("Confirm") { _, _ ->
                if (editText.text.isNotBlank() && !database.workoutAlreadyExists(editText.text.toString()) &&
                    editText.text.toString() != "Push-Pull-Legs" &&
                    editText.text.toString() != "Pull-Push-Legs" && editText.text.toString() != "Powerlifting Focused" && editText.text.toString() != "One Muscle Each Day" &&
                        editText.text.toString() != "pushPullLegs" && editText.text.toString() != "pullPushLegs" && editText.text.toString() != "powerliftingFocused" &&
                        editText.text.toString() != "oneMuscle") {
                    name = editText.text.toString()
                    val image = requireContext().resources.getIdentifier("@drawable/ic_menu_gallery", null, requireContext().packageName)
                    val workout = Workout(name, getCurrentDate(), image)
                    database.addWorkout(workout)
                    workouts = database.getWorkouts()
                    val adapter = WorkoutAdapter(workouts, requireContext())
                    recyclerView.adapter = adapter
                    recyclerView.adapter?.notifyItemInserted(workouts.size)
                }else if(editText.text.isBlank()){
                    Toast.makeText(requireContext(), "Please fill in the blank field", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Workout name already exists", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            alertDialog.show()
        }
        showData(workouts)

        return parentHolder
    }

    private fun showData(workouts : List<Workout>?) {
        val recyclerView : RecyclerView= parentHolder.findViewById(R.id.workoutRecyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = WorkoutAdapter(workouts, requireContext())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(calendar.time)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YourWorkoutsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YourWorkoutsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}