package com.example.fitnessapp.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.Adapters.ExercisesAdapter
import com.example.fitnessapp.Adapters.ExercisesWithImageAdapter
import com.example.fitnessapp.Database.Exercices.Exercise
import com.example.fitnessapp.Database.Photo
import com.example.fitnessapp.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LegsAbsExercisesActivity : AppCompatActivity() {

    private var position = 0
    private lateinit var mAdView : AdManagerAdView
    private val activity = this

    class SpinnerActivity(private val recyclerView: RecyclerView, private val switch: Switch,
                          private val searchBar : EditText,
                          private val legsExercises : List<Exercise>,
                          private val absExercises : List<Exercise>) : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            when {
                parent.getItemAtPosition(pos).equals("Legs") -> {
                    LegsAbsExercisesActivity().showData(recyclerView, switch, legsExercises)
                    searchBar.clearFocus()
                    hideSoftKeyboard()
                }
                parent.getItemAtPosition(pos).equals("Abs") -> {
                    LegsAbsExercisesActivity().showData(recyclerView, switch, absExercises)
                    searchBar.clearFocus()
                    hideSoftKeyboard()
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }
        private fun hideSoftKeyboard() {
            if (currentFocus == null) return
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legs_exercises)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        val spinner : Spinner = findViewById(R.id.legsExercisesSpinner)
        val title : TextView = findViewById(R.id.legsExercisesTitle)
        val color = getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val switch : Switch = findViewById(R.id.legsExercisesSwitch)
        val recyclerView : RecyclerView = findViewById(R.id.legsExercisesRecyclerView)
        val searchBar : EditText = findViewById(R.id.legsExercisesSearchEditText)
        val searchImage : ImageView = findViewById(R.id.legsExercisesSearchImageView)
        val customBackground = getSharedPreferences("Background", Context.MODE_PRIVATE)
        val layout : ConstraintLayout = findViewById(R.id.legsExercisesLayout)
        val image = customBackground.getString("customBackground", null)
        if(image != null){
            layout.background = BitmapDrawable(resources, loadPhoto(image)?.bmp)
        }

        val exercises = ArrayList<Exercise>()

        val legsExercises = ArrayList<Exercise>()
        legsExercises.add(Exercise(181, "Front Squat", "Legs", getString(R.string.front_squat), R.drawable.front_squat))
        legsExercises.add(Exercise(231, "Sumo Deadlift", "Legs", getString(R.string.sumo_deadlift), R.drawable.sumo_deadlift))
        legsExercises.add(Exercise(182, "Bulgarian Split Squat", "Legs", getString(R.string.bulgarian_split_squat), R.drawable.bulgarian_split_squat))
        legsExercises.add(Exercise(183, "Romanian Deadlift", "Legs", getString(R.string.romanian_deadlift), R.drawable.romanian_deadlift))
        legsExercises.add(Exercise(184, "Barbell Squat", "Legs", getString(R.string.barbell_squat), R.drawable.barbell_squat))
        legsExercises.add(Exercise(185, "Dumbbell Stepup", "Legs", getString(R.string.dumbbell_stepup), R.drawable.dumbbell_stepup))
        legsExercises.add(Exercise(186, "Deadlift", "Legs", getString(R.string.deadlift_legs), R.drawable.deadlift))
        legsExercises.add(Exercise(187, "Swiss Ball Leg Curl", "Legs", getString(R.string.swiss_ball_leg_curl), R.drawable.swiss_ball_leg_curl))
        legsExercises.add(Exercise(188, "Single-leg Romanian Deadlift", "Legs", getString(R.string.single_leg_romanian_deadlift), R.drawable.single_leg_romanian_deadlift))
        legsExercises.add(Exercise(189, "Leg Press", "Legs", getString(R.string.leg_press), R.drawable.leg_press))
        legsExercises.add(Exercise(190, "Bodyweight Calf Raise", "Legs", getString(R.string.bodyweight_calf_raise), R.drawable.bodyweight_calf_raise))
        legsExercises.add(Exercise(191, "Walking Lunge", "Legs", getString(R.string.walking_lunge), R.drawable.walking_lunge))
        legsExercises.add(Exercise(192, "Pause Squat", "Legs", getString(R.string.pause_squat), R.drawable.pause_squat))
        legsExercises.add(Exercise(193, "Reverse Lunge", "Legs", getString(R.string.reverse_lunge), R.drawable.reverse_lunge))
        legsExercises.add(Exercise(194, "Dumbbell Squat", "Legs", getString(R.string.dumbbell_squat), R.drawable.dumbbell_squat))
        legsExercises.add(Exercise(195, "Kettlebell Swing ", "Legs", getString(R.string.kettlebell_swing), R.drawable.kettlebell_swing))
        legsExercises.add(Exercise(196, "Jump Squat", "Legs", getString(R.string.jump_squat), R.drawable.jump_squat))
        legsExercises.add(Exercise(197, "Barbell Calf Raise", "Legs", getString(R.string.barbell_calf_raise), R.drawable.barbell_calf_raise))
        legsExercises.add(Exercise(198, "Kneeling Hip Flexor Stretch", "Legs", getString(R.string.kneeling_hip_flexor_stretch), R.drawable.kneeling_hip_flexor_stretch))
        legsExercises.add(Exercise(199, "Skater Squat", "Legs", getString(R.string.skater_squat), R.drawable.skater_squat))
        legsExercises.add(Exercise(200, "Jumping Calf Raise", "Legs", getString(R.string.jumping_calf_raise), R.drawable.jumping_calf_raise))
        legsExercises.add(Exercise(201, "Barbell Hip Thrust", "Legs", getString(R.string.barbell_hip_thrust), R.drawable.barbell_hip_thrust))
        legsExercises.add(Exercise(202, "Glute Bridge Walkout", "Legs", getString(R.string.glute_bridge_walkout), R.drawable.glute_bridge_walkout))
        legsExercises.add(Exercise(203, "Single-leg Glute Bridge", "Legs", getString(R.string.single_leg_glute_bridge), R.drawable.single_leg_glute_bridge))
        legsExercises.add(Exercise(204, "Seated Calf Raise", "Legs", getString(R.string.seated_calf_raise), R.drawable.seated_calf_raise))
        legsExercises.add(Exercise(205, "Swiss Ball Wall Squat", "Legs", getString(R.string.swiss_ball_wall_squat), R.drawable.swiss_ball_wall_squat))
        legsExercises.add(Exercise(206, "Reverse Table-Up", "Legs", getString(R.string.reverse_table_up), R.drawable.reverse_table_up))
        legsExercises.add(Exercise(207, "Kettlebell Press-Out", "Legs", getString(R.string.kettlebell_pressout), R.drawable.kettlebell_pressout))
        legsExercises.add(Exercise(208, "Suspension Trainer Leg Curl", "Legs", getString(R.string.suspension_trainer_leg_curl), R.drawable.suspension_trainer_leg_curl))
        legsExercises.add(Exercise(209, "Overhead Lunge", "Legs", getString(R.string.overhead_lunge), R.drawable.overhead_lunge))
        legsExercises.add(Exercise(210, "Standing Calf Raise", "Legs", getString(R.string.standing_calf_raise), R.drawable.standing_calf_raise))
        legsExercises.add(Exercise(211, "Sumo Squat to Stand", "Legs", getString(R.string.sumo_squat_to_stand), R.drawable.sumo_squat_to_stand))
        legsExercises.add(Exercise(212, "Goblet Squat", "Legs", getString(R.string.goblet_squat), R.drawable.goblet_squat))
        legsExercises.add(Exercise(213, "Lateral Bound", "Legs", getString(R.string.lateral_bound), R.drawable.lateral_bound))
        legsExercises.add(Exercise(214, "Mini Band Monster Walk", "Legs", getString(R.string.mini_band_monster_walk), R.drawable.mini_band_monster_walk))
        legsExercises.add(Exercise(215, "Weighted Wall Sit", "Legs", getString(R.string.weighted_wall_sit), R.drawable.weighted_wall_sit))
        legsExercises.add(Exercise(216, "Pistol Squat", "Legs", getString(R.string.pistol_squat), R.drawable.pistol_squat))
        legsExercises.add(Exercise(217, "Single-leg Clock Squat", "Legs", getString(R.string.single_leg_clock_squat), R.drawable.single_lag_clock_squat))
        legsExercises.add(Exercise(218, "Single-leg Broad Jump", "Legs", getString(R.string.single_leg_broad_jump), R.drawable.single_leg_broad_jump))
        legsExercises.add(Exercise(219, "Split-stance Box Jump", "Legs", getString(R.string.split_stance_box_jump), R.drawable.split_stance_box_jump))
        legsExercises.add(Exercise(220, "180 Squat Jump", "Legs", getString(R.string.one_eighty_squat_jump), R.drawable._80_squat_jump))
        legsExercises.add(Exercise(221, "King Arthur’s Pose", "Legs", getString(R.string.king_arthurs_pose), R.drawable.king_arthurs_pose))
        legsExercises.add(Exercise(222, "Box Jump", "Legs", getString(R.string.box_jump), R.drawable.box_jump))
        legsExercises.add(Exercise(223, "Single-leg Wall Squat", "Legs", getString(R.string.single_leg_wall_squat), R.drawable.single_leg_wall_squat))
        legsExercises.add(Exercise(224, "Banded Deadlift", "Legs", getString(R.string.banded_deadlift), R.drawable.banded_deadlift))
        legsExercises.add(Exercise(225, "Banded Glute Bridge", "Legs", getString(R.string.banded_glute_bridge), R.drawable.banded_glute_bridge))
        legsExercises.add(Exercise(226, "Reverse Airborne Lunge", "Legs", getString(R.string.reverse_airborne_lunge), R.drawable.reverse_airborne_lunge))
        legsExercises.add(Exercise(227, "Inverted Hamstring", "Legs", getString(R.string.inverted_hamstring), R.drawable.inverted_hamstring))
        legsExercises.add(Exercise(228, "World's Greatest Stretch", "Legs", getString(R.string.worlds_greatest_stretch), R.drawable.worlds_greatest_stretch))
        legsExercises.add(Exercise(229, "Broad Jump", "Legs", getString(R.string.broad_jump), R.drawable.broad_jump))
        legsExercises.add(Exercise(230, "Overhead Squat With Dowel Rod", "Legs", getString(R.string.overhead_squat_with_dowel_rod), R.drawable.overhead_squat_with_dowels_rod))
        legsExercises.add(Exercise(292, "Seated Leg Curl", "Legs", getString(R.string.seated_leg_curl), R.drawable.leg_curl))

        val absExercises = ArrayList<Exercise>()
        absExercises.add(Exercise(232, "Ab Wheel Rollout", "Abs", getString(R.string.ab_wheel_rollout), R.drawable.ab_wheel_rollout))
        absExercises.add(Exercise(233, "Arms-High Partial Situp", "Abs", getString(R.string.arms_high_partial_situp), R.drawable.arms_high_partial_situp))
        absExercises.add(Exercise(234, "Barbell Rollout", "Abs", getString(R.string.barbell_rollout), R.drawable.barbell_rollout))
        absExercises.add(Exercise(235, "Barbell Russian Twist", "Abs", getString(R.string.barbell_russian_twist), R.drawable.barbell_russian_twist))
        absExercises.add(Exercise(236, "Swiss Ball Crunch", "Abs", getString(R.string.swiss_ball_crunch), R.drawable.swiss_ball_crunch))
        absExercises.add(Exercise(237, "Dip/Leg Raise Combo", "Abs", getString(R.string.dip_leg_raise_combo), R.drawable.dip_leg_raise_combo))
        absExercises.add(Exercise(238, "Flutter Kick", "Abs", getString(R.string.flutter_kick), R.drawable.flutter_kick))
        absExercises.add(Exercise(239, "Getups", "Abs", getString(R.string.getups), R.drawable.getups))
        absExercises.add(Exercise(240, "Horizontal Cable Woodchop", "Abs", getString(R.string.horizontal_cable_woodchop), R.drawable.horizontal_cable_woodchop))
        absExercises.add(Exercise(241, "Leg Raise", "Abs", getString(R.string.leg_raise), R.drawable.leg_raise))
        absExercises.add(Exercise(242, "Medicine Ball Russian Twist", "Abs", getString(R.string.medicine_ball_russian_twist), R.drawable.medicine_ball_russian_twist))
        absExercises.add(Exercise(243, "Medicine Ball Mountain Climber", "Abs", getString(R.string.medicine_ball_mountain_climber), R.drawable.medicine_ball_mountain_climber))
        absExercises.add(Exercise(244, "Pike to Superman", "Abs", getString(R.string.pike_to_superman), R.drawable.pike_to_superman))
        absExercises.add(Exercise(245, "Plank", "Abs", getString(R.string.plank), R.drawable.plank))
        absExercises.add(Exercise(246, "Pullup to Knee Raise", "Abs", getString(R.string.pullup_to_knee_raise), R.drawable.pullup_to_knee_raise))
        absExercises.add(Exercise(247, "Pushup Rocket", "Abs", getString(R.string.pushup_rocket), R.drawable.pushup_rocket))
        absExercises.add(Exercise(248, "Resisted Reverse Crunch", "Abs", getString(R.string.resisted_reverse_crunch), R.drawable.resisted_reverse_crunch))
        absExercises.add(Exercise(249, "Swiss Ball Rollout", "Abs", getString(R.string.swiss_ball_rollout), R.drawable.swiss_ball_rollout))
        absExercises.add(Exercise(250, "Medicinal Ball Seated Knee Tuck", "Abs", getString(R.string.medicinal_ball_seated_knee_tuck), R.drawable.medicinal_ball_seated_knee_tuck))
        absExercises.add(Exercise(251, "Side Plank", "Abs", getString(R.string.side_plank), R.drawable.side_plank))
        absExercises.add(Exercise(252, "Sprinter", "Abs", getString(R.string.sprinter), R.drawable.sprinter))
        absExercises.add(Exercise(253, "Situp and throw", "Abs", getString(R.string.situp_and_throw), R.drawable.situp_and_throw))
        absExercises.add(Exercise(254, "Star Plank", "Abs", getString(R.string.star_plank), R.drawable.star_plank))
        absExercises.add(Exercise(255, "Straight-Leg Barbell Situp", "Abs", getString(R.string.straight_leg_barbell_situp), R.drawable.straight_leg_barbell_situp))
        absExercises.add(Exercise(256, "Suitcase Deadlift", "Abs", getString(R.string.suitcase_deadlift), R.drawable.suitcase_deadlift))
        absExercises.add(Exercise(257, "Swiss Ball Plank Circle", "Abs", getString(R.string.swiss_ball_plank_circle), R.drawable.swiss_ball_plank_circle))
        absExercises.add(Exercise(258, "Swiss Ball V-Up and Pass", "Abs", getString(R.string.swiss_ball_v_up_and_pass), R.drawable.swiss_ball_v_up_and_pass))
        absExercises.add(Exercise(259, "Medicine Ball V-Up", "Abs", getString(R.string.medicine_ball_v_up), R.drawable.medicine_ball_v_up))
        absExercises.add(Exercise(260, "Seated Sprinter", "Abs", getString(R.string.seated_sprinter), R.drawable.seated_sprinter))
        absExercises.add(Exercise(261, "Half Kneeling Chop", "Abs", getString(R.string.half_kneeling_chop), R.drawable.half_kneeling_chop))
        absExercises.add(Exercise(262, "Boat Pose", "Abs", getString(R.string.boat_pose), R.drawable.boat_pose))
        absExercises.add(Exercise(263, "Oblique Mountain Climbers", "Abs", getString(R.string.oblique_mountain_climbers), R.drawable.oblique_mountain_climber))
        absExercises.add(Exercise(264, "Hanging Windshield Wiper", "Abs", getString(R.string.hanging_windshield_wiper), R.drawable.hanging_windshield_wiper))
        absExercises.add(Exercise(265, "Banded Reverse Crunch", "Abs", getString(R.string.banded_reverse_crunch), R.drawable.banded_reverse_crunch))
        absExercises.add(Exercise(266, "Waiter's Walk", "Abs", getString(R.string.waiters_walk_abs), R.drawable.waiters_walk))
        absExercises.add(Exercise(267, "T Pushup", "Abs", getString(R.string.t_pushup_abs), R.drawable.t_pushup))
        absExercises.add(Exercise(268, "Bird Dog Crunch", "Abs", getString(R.string.bird_dog_crunch), R.drawable.bird_dog_crunch))
        absExercises.add(Exercise(269, "One-Arm Farmer’s Carry", "Abs", getString(R.string.one_arm_farmers_carry), R.drawable.one_arm_farmers_carry))
        absExercises.add(Exercise(270, "Burpee", "Abs", getString(R.string.burpee_abs), R.drawable.burpee))
        absExercises.add(Exercise(271, "Swiss Ball Plate Crunch", "Abs", getString(R.string.swiss_ball_plate_crunch), R.drawable.swiss_ball_plate_crunch))
        absExercises.add(Exercise(272, "Medicine Ball Rotational Throw", "Abs", getString(R.string.medicine_ball_rotational_throw_abs), R.drawable.medicine_ball_rotational_throw))
        absExercises.add(Exercise(273, "Medicine Ball Slam", "Abs", getString(R.string.medicine_ball_slam), R.drawable.medicine_ball_slam))
        absExercises.add(Exercise(274, "Corkscrew", "Abs", getString(R.string.corkscrew), R.drawable.corkscrew))
        absExercises.add(Exercise(275, "Cable Crunch", "Abs", getString(R.string.cable_crunch), R.drawable.cable_crunch))
        absExercises.add(Exercise(276, "Side Plank Row", "Abs", getString(R.string.side_plank_row), R.drawable.side_plank_row))
        absExercises.add(Exercise(277, "Single-leg Oblique Dip", "Abs", getString(R.string.single_leg_oblique_dip), R.drawable.single_leg_oblique_dip))
        absExercises.add(Exercise(278, "Plyo Plank Shuffle", "Abs", getString(R.string.plyo_plank_shuffle), R.drawable.plyo_plank_shuffle))
        absExercises.add(Exercise(279, "Front Squat", "Abs", getString(R.string.front_squat_abs), R.drawable.front_squat))
        absExercises.add(Exercise(280, "Weighted Situp", "Abs", getString(R.string.weighted_situp), R.drawable.weighted_situp))
        absExercises.add(Exercise(281, "Kettlebell Plank Sweeps", "Abs", getString(R.string.kettlebell_plank_sweeps), R.drawable.kettlebell_plank_sweep))


        searchImage.setOnClickListener {
            searchBar.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
        }

        window?.statusBarColor = color.getInt("color", Color.BLUE)
        actionbar?.setBackgroundDrawable(ColorDrawable(color.getInt("color", Color.BLUE)))
        title.setTextColor(color.getInt("color", Color.BLUE))
        ArrayAdapter.createFromResource(this, R.array.legsExercisesArray, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = SpinnerActivity(
            recyclerView, switch, searchBar,
            legsExercises,
            absExercises)


        switch.setOnCheckedChangeListener { _, isChecked ->
            position = spinner.selectedItemPosition
            when(position){
                0 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(legsExercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesAdapter(legsExercises, this, this)
                        switch.text = "Show Thumbnails"
                    }
                }
                1 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(absExercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesAdapter(absExercises, this, this)
                        switch.text = "Show Thumbnails"
                    }
                }
            }
        }
        searchBar.doOnTextChanged { text, _, _, _ ->
            position = spinner.selectedItemPosition
            when(position){
                0 -> {
                    val list = ArrayList<Exercise>()
                    if(!text.isNullOrBlank()) {
                        for (i in legsExercises.indices) {
                            val word = legsExercises[i].name.split("\\s".toRegex()).toTypedArray()
                            for (j in word.indices) {
                                if (word[j].startsWith(text, true)) {
                                    list.add(legsExercises[i])
                                }
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, legsExercises)
                    }
                }
                1 -> {
                    val list = ArrayList<Exercise>()
                    if(!text.isNullOrBlank()) {
                        for (i in absExercises.indices) {
                            if (absExercises[i].name.startsWith(text, true)) {
                                list.add(absExercises[i])
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, exercises)
                    }
                }
            }
        }

        val backToWorkoutButton : FloatingActionButton = findViewById(R.id.legsBackToWorkoutButton)
        backToWorkoutButton.backgroundTintList = ColorStateList.valueOf(color.getInt("color", Color.BLUE))
        backToWorkoutButton.setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }
    }
    private fun showData(recyclerView: RecyclerView, @SuppressLint("UseSwitchCompatOrMaterialCode") switch : Switch, exercises : List<Exercise>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = if(switch.isChecked){
                ExercisesWithImageAdapter(exercises, context, activity)
            }else{
                ExercisesAdapter(exercises, context, activity)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun loadPhoto(filename: String) : Photo? {
        val files = filesDir.listFiles()
        files?.filter { it.canRead() && it.isFile && it.name.startsWith(filename) && it.name.endsWith(".jpg") }?.map {
            val bytes = it.readBytes()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            return Photo(it.name, bmp)
        }
        return null
    }
}