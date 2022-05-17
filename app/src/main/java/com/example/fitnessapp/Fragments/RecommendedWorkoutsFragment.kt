package com.example.fitnessapp.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.Adapters.RecommendedWorkoutAdapter
import com.example.fitnessapp.Database.Exercices.ChosenExercise
import com.example.fitnessapp.Database.Workouts.Workout
import com.example.fitnessapp.Database.Workouts.WorkoutDB
import com.example.fitnessapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [RecommendedWorkoutsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecommendedWorkoutsFragment : Fragment() {

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var parentHolder : View
    private lateinit var title : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        parentHolder = inflater.inflate(R.layout.fragment_recommended_workouts, container, false)
        title = parentHolder.findViewById(R.id.recommendedWorkoutsTitle)
        val color = requireContext().getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val database = WorkoutDB.get(requireContext()).getChosenExerciseDAO()
        val sharedPreferences = requireContext().getSharedPreferences("Recommended", Context.MODE_PRIVATE)
        if(!sharedPreferences.getBoolean("recommended", false)){
            val pushPullLegs = "Push-Pull-Legs"
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 0, "Bench Press", R.drawable.bench_press, "Chest", 10, "", 0, 4, "Monday", getString(R.string.bench_press), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 5, "Incline Dumbbell Press", R.drawable.incline_dumbbell_press, "Chest", 10, "", 0, 4, "Monday", getString(R.string.incline_dumbbell_press), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 38, "Dip", R.drawable.dips, "Chest", 0, "", 10, 4, "Monday", getString(R.string.dip), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 3, "Incline Dumbbell Flies", R.drawable.incline_dumbbell_flies, "Chest", 10, "", 0, 4, "Monday", getString(R.string.incline_dumbbell_flies), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 25, "Pec Deck", R.drawable.pec_deck, "Chest", 10, "", 0, 4, "Monday", getString(R.string.pec_deck), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 283, "Single Arm Cable Extension", R.drawable.single_arm_cable_extension, "Triceps", 10, "", 0, 4, "Monday", getString(R.string.single_arm_cable_extension), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 284, "Reverse Grip Cable Extension", R.drawable.reverse_extension, "Triceps", 10, "", 0, 4, "Monday", getString(R.string.reverse_grip_cable_extension), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 37, "One-Arm Overhead Extension", R.drawable.one_erm_overhead_extensions, "Triceps", 10, "", 0, 4, "Monday", getString(R.string.one_arm_overhead_extension), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 289, "Seated Overhead Dumbbell Press", R.drawable.seated_dumbbell_shoulder_press, "Shoulders", 10, "", 0, 4, "Monday", getString(R.string.seated_overhead_dumbbell_press), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 287, "Dumbbell Lateral Raise", R.drawable.dumbbell_lateral_raise, "Shoulders", 10, "", 0, 4, "Monday", getString(R.string.dumbbell_lateral_raise), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Monday" + 288, "Dumbbell Front Raise", R.drawable.dumbbell_front_raise, "Shoulders", 10, "", 0, 4, "Monday", getString(R.string.dumbbell_front_raise), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Tuesday" + 125, "Lat Pulldown", R.drawable.lat_pulldown, "Back", 10, "", 0, 4, "Tuesday", getString(R.string.lat_pulldown), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Tuesday" + 112, "Seated Cable Row", R.drawable.seated_cable_row, "Back", 10, "", 0, 4, "Tuesday", getString(R.string.seated_cable_row), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Tuesday" + 289, "Barbell Row", R.drawable.barbell_row, "Back", 10, "", 0, 4, "Tuesday", getString(R.string.barbell_row), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Tuesday" + 290, "T-Bar Row", R.drawable.t_bar_row, "Back", 10, "", 0, 4, "Tuesday", getString(R.string.t_bar_row), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Tuesday" + 145, "Reverse Curl", R.drawable.reverse_curl, "Biceps", 10, "", 0, 4, "Tuesday", getString(R.string.reverse_curl), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Tuesday" + 146, "Wide-Grip Curl", R.drawable.wide_grip_curl, "Biceps", 10, "", 0, 4, "Tuesday", getString(R.string.wide_grip_curl), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Tuesday" + 149, "Dumbbell Curl", R.drawable.dumbbell_curl, "Biceps", 10, "", 0, 4, "Tuesday", getString(R.string.dumbbell_curl), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Wednesday" + 231, "Sumo Deadlift", R.drawable.sumo_deadlift, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.sumo_deadlift), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Wednesday" + 201, "Barbell Hip Thrust", R.drawable.barbell_hip_thrust, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.barbell_hip_thrust), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Wednesday" + 184, "Barbell Squat", R.drawable.barbell_squat, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.barbell_squat), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Wednesday" + 182, "Bulgarian Split Squat", R.drawable.bulgarian_split_squat, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.bulgarian_split_squat), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 0, "Bench Press", R.drawable.bench_press, "Chest", 10, "", 0, 4, "Thursday", getString(R.string.bench_press), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 1, "Dumbbell Press", R.drawable.dumbbell_press, "Chest", 10, "", 0, 4, "Thursday", getString(R.string.dumbbell_press), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 7, "Dumbbell Flies", R.drawable.dumbbell_flies, "Chest", 10, "", 0, 4, "Thursday", getString(R.string.dumbbell_flies), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 6, "Chest Press Machine", R.drawable.chest_press_machine, "Chest", 10, "", 0, 4, "Thursday", getString(R.string.chest_press_machine), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 282, "Skull Crushers", R.drawable.skull_crushers, "Triceps", 10, "", 0, 4, "Thursday", getString(R.string.skull_crushers), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 285, "Overhead Triceps Extension", R.drawable.dumbbell_triceps_extension, "Triceps", 10, "", 0, 4, "Thursday", getString(R.string.overhead_triceps_extension), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 36, "Underhand Kickback", R.drawable.underhand_kickback, "Triceps", 10, "", 0, 4, "Thursday", getString(R.string.underhand_kickback), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 81, "Barbell Overhead Press", R.drawable.barbell_overhead_press, "Shoulders", 10, "", 0, 4, "Thursday", getString(R.string.barbell_overhead_press), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 47, "Trap Raise", R.drawable.trap_raise, "Shoulders", 10, "", 0, 4, "Thursday", getString(R.string.trap_raise), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Thursday" + 73, "Upright Row", R.drawable.upright_row, "Shoulders", 10, "", 0, 4, "Thursday", getString(R.string.upright_row), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Friday" + 286, "Close-Grip Lat Pulldown", R.drawable.close_grip_lat_pulldown, "Back", 10, "", 0, 4, "Friday", getString(R.string.close_grip_lat_pulldown), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Friday" + 291, "Wide-Grip Seated Cable Row", R.drawable.wide_grip_seated_cable_row, "Back", 10, "", 0, 4, "Friday", getString(R.string.wide_grip_seated_cable_row), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Friday" + 94, "Dumbbell Row", R.drawable.dumbbell_row, "Back", 10, "", 0, 4, "Friday", getString(R.string.dumbbell_row), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Friday" + 103, "Wide-Grip Pullup", R.drawable.wide_grip_pullup, "Back", 10, "", 0, 4, "Friday", getString(R.string.wide_grip_pullup), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Friday" + 144, "EZ-Bar Preacher Curl", R.drawable.ez_bar_preacher_curl, "Biceps", 10, "", 0, 4, "Friday", getString(R.string.ez_bar_preacher_curl), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Friday" + 147, "Close-Grip Curl", R.drawable.close_grip_curl, "Biceps", 10, "", 0, 4, "Friday", getString(R.string.close_grip_curl), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Friday" + 151, "Hammer Curl", R.drawable.hammer_curl, "Biceps", 10, "", 0, 4, "Friday", getString(R.string.hammer_curl), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Saturday" + 183, "Romanian Deadlift", R.drawable.romanian_deadlift, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.romanian_deadlift), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Saturday" + 189, "Leg Press", R.drawable.leg_press, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.leg_press), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Saturday" + 292, "Seated Leg Curl", R.drawable.leg_curl, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.seated_leg_curl), pushPullLegs))
            database.addChosenExercise(ChosenExercise(pushPullLegs + "Saturday" + 215, "Weighted Wall Sit", R.drawable.weighted_wall_sit, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.weighted_wall_sit), pushPullLegs))
            val pullPushLegs = "Pull-Push-Legs"
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Monday" + 125, "Lat Pulldown", R.drawable.lat_pulldown, "Back", 10, "", 0, 4, "Monday", getString(R.string.lat_pulldown), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Monday" + 112, "Seated Cable Row", R.drawable.seated_cable_row, "Back", 10, "", 0, 4, "Monday", getString(R.string.seated_cable_row), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Monday" + 289, "Barbell Row", R.drawable.barbell_row, "Back", 10, "", 0, 4, "Monday", getString(R.string.barbell_row), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Monday" + 290, "T-Bar Row", R.drawable.t_bar_row, "Back", 10, "", 0, 4, "Monday", getString(R.string.t_bar_row), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Monday" + 145, "Reverse Curl", R.drawable.reverse_curl, "Biceps", 10, "", 0, 4, "Monday", getString(R.string.reverse_curl), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Monday" + 146, "Wide-Grip Curl", R.drawable.wide_grip_curl, "Biceps", 10, "", 0, 4, "Monday", getString(R.string.wide_grip_curl), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Monday" + 149, "Dumbbell Curl", R.drawable.dumbbell_curl, "Biceps", 10, "", 0, 4, "Monday", getString(R.string.dumbbell_curl), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 0, "Bench Press", R.drawable.bench_press, "Chest", 10, "", 0, 4, "Tuesday", getString(R.string.bench_press), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 5, "Incline Dumbbell Press", R.drawable.incline_dumbbell_press, "Chest", 10, "", 0, 4, "Tuesday", getString(R.string.incline_dumbbell_press), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 38, "Dip", R.drawable.dips, "Chest", 0, "", 10, 4, "Tuesday", getString(R.string.dip), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 3, "Incline Dumbbell Flies", R.drawable.incline_dumbbell_flies, "Chest", 10, "", 0, 4, "Tuesday", getString(R.string.incline_dumbbell_flies), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 25, "Pec Deck", R.drawable.pec_deck, "Chest", 10, "", 0, 4, "Tuesday", getString(R.string.pec_deck), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 283, "Single Arm Cable Extension", R.drawable.single_arm_cable_extension, "Triceps", 10, "", 0, 4, "Tuesday", getString(R.string.single_arm_cable_extension), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 284, "Reverse Grip Cable Extension", R.drawable.reverse_extension, "Triceps", 10, "", 0, 4, "Tuesday", getString(R.string.reverse_grip_cable_extension), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 37, "One-Arm Overhead Extension", R.drawable.one_erm_overhead_extensions, "Triceps", 10, "", 0, 4, "Tuesday", getString(R.string.one_arm_overhead_extension), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 289, "Seated Overhead Dumbbell Press", R.drawable.seated_dumbbell_shoulder_press, "Shoulders", 10, "", 0, 4, "Tuesday", getString(R.string.seated_overhead_dumbbell_press), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 287, "Dumbbell Lateral Raise", R.drawable.dumbbell_lateral_raise, "Shoulders", 10, "", 0, 4, "Tuesday", getString(R.string.dumbbell_lateral_raise), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Tuesday" + 288, "Dumbbell Front Raise", R.drawable.dumbbell_front_raise, "Shoulders", 10, "", 0, 4, "Tuesday", getString(R.string.dumbbell_front_raise), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Wednesday" + 231, "Sumo Deadlift", R.drawable.sumo_deadlift, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.sumo_deadlift), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Wednesday" + 201, "Barbell Hip Thrust", R.drawable.barbell_hip_thrust, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.barbell_hip_thrust), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Wednesday" + 184, "Barbell Squat", R.drawable.barbell_squat, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.barbell_squat), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Wednesday" + 182, "Bulgarian Split Squat", R.drawable.bulgarian_split_squat, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.bulgarian_split_squat), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Thursday" + 286, "Close-Grip Lat Pulldown", R.drawable.close_grip_lat_pulldown, "Back", 10, "", 0, 4, "Thursday", getString(R.string.close_grip_lat_pulldown), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Thursday" + 291, "Wide-Grip Seated Cable Row", R.drawable.wide_grip_seated_cable_row, "Back", 10, "", 0, 4, "Thursday", getString(R.string.wide_grip_seated_cable_row), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Thursday" + 94, "Dumbbell Row", R.drawable.dumbbell_row, "Back", 10, "", 0, 4, "Thursday", getString(R.string.dumbbell_row), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Thursday" + 103, "Wide-Grip Pullup", R.drawable.wide_grip_pullup, "Back", 10, "", 0, 4, "Thursday", getString(R.string.wide_grip_pullup), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Thursday" + 144, "EZ-Bar Preacher Curl", R.drawable.ez_bar_preacher_curl, "Biceps", 10, "", 0, 4, "Thursday", getString(R.string.ez_bar_preacher_curl), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Thursday" + 147, "Close-Grip Curl", R.drawable.close_grip_curl, "Biceps", 10, "", 0, 4, "Thursday", getString(R.string.close_grip_curl), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Thursday" + 151, "Hammer Curl", R.drawable.hammer_curl, "Biceps", 10, "", 0, 4, "Thursday", getString(R.string.hammer_curl), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 0, "Bench Press", R.drawable.bench_press, "Chest", 10, "", 0, 4, "Friday", getString(R.string.bench_press), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 1, "Dumbbell Press", R.drawable.dumbbell_press, "Chest", 10, "", 0, 4, "Friday", getString(R.string.dumbbell_press), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 7, "Dumbbell Flies", R.drawable.dumbbell_flies, "Chest", 10, "", 0, 4, "Friday", getString(R.string.dumbbell_flies), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 6, "Chest Press Machine", R.drawable.chest_press_machine, "Chest", 10, "", 0, 4, "Friday", getString(R.string.chest_press_machine), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 282, "Skull Crushers", R.drawable.skull_crushers, "Triceps", 10, "", 0, 4, "Friday", getString(R.string.skull_crushers), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 285, "Overhead Triceps Extension", R.drawable.dumbbell_triceps_extension, "Triceps", 10, "", 0, 4, "Friday", getString(R.string.overhead_triceps_extension), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 36, "Underhand Kickback", R.drawable.underhand_kickback, "Triceps", 10, "", 0, 4, "Friday", getString(R.string.underhand_kickback), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 81, "Barbell Overhead Press", R.drawable.barbell_overhead_press, "Shoulders", 10, "", 0, 4, "Friday", getString(R.string.barbell_overhead_press), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 47, "Trap Raise", R.drawable.trap_raise, "Shoulders", 10, "", 0, 4, "Friday", getString(R.string.trap_raise), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Friday" + 73, "Upright Row", R.drawable.upright_row, "Shoulders", 10, "", 0, 4, "Friday", getString(R.string.upright_row), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Saturday" + 183, "Romanian Deadlift", R.drawable.romanian_deadlift, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.romanian_deadlift), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Saturday" + 189, "Leg Press", R.drawable.leg_press, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.leg_press), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Saturday" + 292, "Seated Leg Curl", R.drawable.leg_curl, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.seated_leg_curl), pullPushLegs))
            database.addChosenExercise(ChosenExercise(pullPushLegs + "Saturday" + 215, "Weighted Wall Sit", R.drawable.weighted_wall_sit, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.weighted_wall_sit), pullPushLegs))
            val powerliftingFocused = "Powerlifting Focused"
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Monday" + 0, "Bench Press", R.drawable.bench_press, "Chest", 3, "", 0, 4, "Monday", getString(R.string.bench_press), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Monday" + 5, "Incline Dumbbell Press", R.drawable.incline_dumbbell_press, "Chest", 6, "", 0, 4, "Monday", getString(R.string.incline_dumbbell_press), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Monday" + 3, "Incline Dumbbell Flies", R.drawable.incline_dumbbell_flies, "Chest", 8, "", 0, 4, "Monday", getString(R.string.incline_dumbbell_flies), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Monday" + 25, "Pec Deck", R.drawable.pec_deck, "Chest", 8, "", 0, 4, "Monday", getString(R.string.pec_deck), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Tuesday" + 125, "Lat Pulldown", R.drawable.lat_pulldown, "Back", 8, "", 0, 4, "Tuesday", getString(R.string.lat_pulldown), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Tuesday" + 112, "Seated Cable Row", R.drawable.seated_cable_row, "Back", 8, "", 0, 4, "Tuesday", getString(R.string.seated_cable_row), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Tuesday" + 289, "Barbell Row", R.drawable.barbell_row, "Back", 8, "", 0, 4, "Tuesday", getString(R.string.barbell_row), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Tuesday" + 290, "T-Bar Row", R.drawable.t_bar_row, "Back", 6, "", 0, 4, "Tuesday", getString(R.string.t_bar_row), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Wednesday" + 231, "Sumo Deadlift", R.drawable.sumo_deadlift, "Legs", 2, "", 0, 4, "Wednesday", getString(R.string.sumo_deadlift), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Wednesday" + 184, "Barbell Squat", R.drawable.barbell_squat, "Legs", 6, "", 0, 4, "Wednesday", getString(R.string.barbell_squat), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Wednesday" + 201, "Barbell Hip Thrust", R.drawable.barbell_hip_thrust, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.barbell_hip_thrust), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Wednesday" + 182, "Bulgarian Split Squat", R.drawable.bulgarian_split_squat, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.bulgarian_split_squat), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Thursday" + 0, "Bench Press", R.drawable.bench_press, "Chest", 3, "", 0, 4, "Thursday", getString(R.string.bench_press), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Thursday" + 1, "Dumbbell Press", R.drawable.dumbbell_press, "Chest", 4, "", 0, 4, "Thursday", getString(R.string.dumbbell_press), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Thursday" + 7, "Dumbbell Flies", R.drawable.dumbbell_flies, "Chest", 8, "", 0, 4, "Thursday", getString(R.string.dumbbell_flies), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Thursday" + 6, "Chest Press Machine", R.drawable.chest_press_machine, "Chest", 8, "", 0, 4, "Thursday", getString(R.string.chest_press_machine), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Friday" + 286, "Close-Grip Lat Pulldown", R.drawable.close_grip_lat_pulldown, "Back", 6, "", 0, 4, "Friday", getString(R.string.close_grip_lat_pulldown), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Friday" + 291, "Wide-Grip Seated Cable Row", R.drawable.wide_grip_seated_cable_row, "Back", 6, "", 0, 4, "Friday", getString(R.string.wide_grip_seated_cable_row), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Friday" + 94, "Dumbbell Row", R.drawable.dumbbell_row, "Back", 8, "", 0, 4, "Friday", getString(R.string.dumbbell_row), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Friday" + 103, "Wide-Grip Pullup", R.drawable.wide_grip_pullup, "Back", 4, "", 0, 4, "Friday", getString(R.string.wide_grip_pullup), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Saturday" + 184, "Barbell Squat", R.drawable.barbell_squat, "Legs", 2, "", 0, 4, "Saturday", getString(R.string.barbell_squat), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Saturday" + 189, "Leg Press", R.drawable.leg_press, "Legs", 5, "", 0, 4, "Saturday", getString(R.string.leg_press), powerliftingFocused))
            database.addChosenExercise(ChosenExercise(powerliftingFocused + "Saturday" + 292, "Seated Leg Curl", R.drawable.leg_curl, "Legs", 10, "", 0, 4, "Saturday", getString(R.string.seated_leg_curl), powerliftingFocused))
            val oneMuscle = "One Muscle Each Day"
            database.addChosenExercise(ChosenExercise(oneMuscle + "Monday" + 0, "Bench Press", R.drawable.bench_press, "Chest", 12, "", 0, 4, "Monday", getString(R.string.bench_press), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Monday" + 5, "Incline Dumbbell Press", R.drawable.incline_dumbbell_press, "Chest", 15, "", 0, 4, "Monday", getString(R.string.incline_dumbbell_press), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Monday" + 38, "Dip", R.drawable.dips, "Chest", 0, "", 15, 4, "Monday", getString(R.string.dip), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Monday" + 3, "Incline Dumbbell Flies", R.drawable.incline_dumbbell_flies, "Chest", 15, "", 0, 4, "Monday", getString(R.string.incline_dumbbell_flies), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Monday" + 25, "Pec Deck", R.drawable.pec_deck, "Chest", 15, "", 0, 4, "Monday", getString(R.string.pec_deck), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Tuesday" + 125, "Lat Pulldown", R.drawable.lat_pulldown, "Back", 15, "", 0, 4, "Tuesday", getString(R.string.lat_pulldown), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Tuesday" + 112, "Seated Cable Row", R.drawable.seated_cable_row, "Back", 15, "", 0, 4, "Tuesday", getString(R.string.seated_cable_row), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Tuesday" + 289, "Barbell Row", R.drawable.barbell_row, "Back", 15, "", 0, 4, "Tuesday", getString(R.string.barbell_row), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Tuesday" + 290, "T-Bar Row", R.drawable.t_bar_row, "Back", 15, "", 0, 4, "Tuesday", getString(R.string.t_bar_row), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Wednesday" + 231, "Sumo Deadlift", R.drawable.sumo_deadlift, "Legs", 10, "", 0, 4, "Wednesday", getString(R.string.sumo_deadlift), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Wednesday" + 201, "Barbell Hip Thrust", R.drawable.barbell_hip_thrust, "Legs", 12, "", 0, 4, "Wednesday", getString(R.string.barbell_hip_thrust), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Wednesday" + 184, "Barbell Squat", R.drawable.barbell_squat, "Legs", 15, "", 0, 4, "Wednesday", getString(R.string.barbell_squat), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Wednesday" + 182, "Bulgarian Split Squat", R.drawable.bulgarian_split_squat, "Legs", 20, "", 0, 4, "Wednesday", getString(R.string.bulgarian_split_squat), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Thursday" + 282, "Skull Crushers", R.drawable.skull_crushers, "Triceps", 12, "", 0, 4, "Thursday", getString(R.string.skull_crushers), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Thursday" + 285, "Overhead Triceps Extension", R.drawable.dumbbell_triceps_extension, "Triceps", 12, "", 0, 4, "Thursday", getString(R.string.overhead_triceps_extension), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Thursday" + 36, "Underhand Kickback", R.drawable.underhand_kickback, "Triceps", 15, "", 0, 4, "Thursday", getString(R.string.underhand_kickback), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Thursday" + 283, "Single Arm Cable Extension", R.drawable.single_arm_cable_extension, "Triceps", 15, "", 0, 4, "Thursday", getString(R.string.single_arm_cable_extension), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Friday" + 151, "Hammer Curl", R.drawable.hammer_curl, "Biceps", 15, "", 0, 4, "Friday", getString(R.string.hammer_curl), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Friday" + 145, "Reverse Curl", R.drawable.reverse_curl, "Biceps", 15, "", 0, 4, "Friday", getString(R.string.reverse_curl), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Friday" + 146, "Wide-Grip Curl", R.drawable.wide_grip_curl, "Biceps", 15, "", 0, 4, "Friday", getString(R.string.wide_grip_curl), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Friday" + 149, "Dumbbell Curl", R.drawable.dumbbell_curl, "Biceps", 15, "", 0, 4, "Friday", getString(R.string.dumbbell_curl), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Saturday" + 232, "Ab Wheel Rollout", R.drawable.ab_wheel_rollout, "Abs", 15, "", 0, 4, "Saturday", getString(R.string.ab_wheel_rollout), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Saturday" + 241, "Leg Raise", R.drawable.leg_raise, "Abs", 15, "", 0, 4, "Saturday", getString(R.string.leg_raise), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Saturday" + 242, "Medicine Ball Russian Twist", R.drawable.medicine_ball_russian_twist, "Abs", 20, "", 0, 4, "Saturday", getString(R.string.medicine_ball_russian_twist), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Saturday" + 251, "Side Plank", R.drawable.side_plank, "Abs", 60, "", 0, 4, "Saturday", getString(R.string.side_plank), oneMuscle))
            database.addChosenExercise(ChosenExercise(oneMuscle + "Saturday" + 245, "Plank", R.drawable.plank, "Abs", 60, "", 0, 4, "Saturday", getString(R.string.plank), oneMuscle))
            sharedPreferences.edit().putBoolean("recommended", true).apply()
        }
        title.setTextColor(color.getInt("color", Color.BLUE))
        val recommendedWorkoutList = ArrayList<Workout>()
        recommendedWorkoutList.add(Workout("Push-Pull-Legs", "", R.drawable.push_pull_legs_image))
        recommendedWorkoutList.add(Workout("Pull-Push-Legs", "", R.drawable.pull_push_legs_image))
        recommendedWorkoutList.add(Workout("Powerlifting Focused", "", R.drawable.powerlifting_image))
        recommendedWorkoutList.add(Workout("One Muscle Each Day", "", R.drawable.one_muscle_each_day_image))
        showData(recommendedWorkoutList)
        return parentHolder
    }

    private fun showData(workouts : List<Workout>?) {
        val recyclerView : RecyclerView = parentHolder.findViewById(R.id.recommendedWorkoutsRecyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = RecommendedWorkoutAdapter(workouts, requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecommendedWorkoutsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}