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
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

class PushExercisesActivity : AppCompatActivity() {

    private var position = 0
    private lateinit var mAdView : AdManagerAdView
    private val activity = this

    class SpinnerActivity(private val recyclerView: RecyclerView, private val switch: Switch,
                          private val searchBar : EditText,
                          private val chestExercises : List<Exercise>,
                          private val tricepsExercises : List<Exercise>,
                          private val shouldersExercises : List<Exercise>) : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            when {
                parent.getItemAtPosition(pos).equals("Chest") -> {
                    PushExercisesActivity().showData(recyclerView, switch, chestExercises)
                    searchBar.clearFocus()
                    hideSoftKeyboard()
                }
                parent.getItemAtPosition(pos).equals("Triceps") -> {
                    PushExercisesActivity().showData(recyclerView, switch, tricepsExercises)
                    searchBar.clearFocus()
                    hideSoftKeyboard()
                }
                parent.getItemAtPosition(pos).equals("Shoulders") -> {
                    PushExercisesActivity().showData(recyclerView, switch, shouldersExercises)
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
        setContentView(R.layout.activity_push_exercises)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        val spinner : Spinner = findViewById(R.id.pushExercisesSpinner)
        val title : TextView = findViewById(R.id.pushExercisesTitle)
        val color = getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val switch : Switch = findViewById(R.id.pushExercisesSwitch)
        val recyclerView : RecyclerView = findViewById(R.id.pushExercisesRecyclerView)
        val searchBar : EditText = findViewById(R.id.pushExercisesSearchEditText)
        val searchImage : ImageView = findViewById(R.id.pushExercisesSearchImageView)
        val customBackground = getSharedPreferences("Background", Context.MODE_PRIVATE)
        val layout : ConstraintLayout = findViewById(R.id.pushExercisesLayout)
        val image = customBackground.getString("customBackground", null)
        if(image != null){
            layout.background = BitmapDrawable(resources, loadPhoto(image)?.bmp)
        }

        searchImage.setOnClickListener {
            searchBar.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
        }

        window?.statusBarColor = color.getInt("color", Color.BLUE)
        actionbar?.setBackgroundDrawable(ColorDrawable(color.getInt("color", Color.BLUE)))
        title.setTextColor(color.getInt("color", Color.BLUE))
        ArrayAdapter.createFromResource(this, R.array.pushExercisesArray, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val exercises = ArrayList<Exercise>()
        exercises.add(Exercise(0, "Bench Press", "Chest", getString(R.string.bench_press), R.drawable.bench_press))
        exercises.add(Exercise(1, "Dumbbell Press", "Chest", getString(R.string.dumbbell_press), R.drawable.dumbbell_press))
        exercises.add(Exercise(2, "Smith Machine Incline Press", "Chest", getString(R.string.smith_machine_incline_press), R.drawable.smith_machine_incline_press))
        exercises.add(Exercise(3, "Incline Dumbbell Flies", "Chest", getString(R.string.incline_dumbbell_flies), R.drawable.incline_dumbbell_flies))
        exercises.add(Exercise(4, "Cable Crossover", "Chest", getString(R.string.cable_crossover), R.drawable.cable_crossover))
        exercises.add(Exercise(5, "Incline Dumbbell Press", "Chest", getString(R.string.incline_dumbbell_press), R.drawable.incline_dumbbell_press))
        exercises.add(Exercise(6, "Chest Press Machine", "Chest", getString(R.string.chest_press_machine), R.drawable.chest_press_machine))
        exercises.add(Exercise(7, "Dumbbell Flies", "Chest", getString(R.string.dumbbell_flies), R.drawable.dumbbell_flies))
        exercises.add(Exercise(8, "Low-Cable Crossover", "Chest", getString(R.string.low_cable_crossover), R.drawable.low_cable_crossover))
        exercises.add(Exercise(9, "Low-Incline Press", "Chest", getString(R.string.low_incline_press), R.drawable.low_incline_press))
        exercises.add(Exercise(10, "Speed Bench Press", "Chest", getString(R.string.speed_bench_press), R.drawable.speed_bench_press))
        exercises.add(Exercise(11, "Landmine Press", "Chest", getString(R.string.landmine_press), R.drawable.landmine_press))
        exercises.add(Exercise(12, "Floor Press", "Chest", getString(R.string.floor_press), R.drawable.floor_press))
        exercises.add(Exercise(13, "Prone Flies", "Chest", getString(R.string.prone_flies), R.drawable.prone_flies))
        exercises.add(Exercise(14, "Pullover", "Chest", getString(R.string.pullover), R.drawable.pullover))
        exercises.add(Exercise(15, "Plate Pressout", "Chest", getString(R.string.plate_pressout), R.drawable.plate_pressout))
        exercises.add(Exercise(16, "3-way Flie", "Chest", getString(R.string.three_way_flie), R.drawable.__way_flye))
        exercises.add(Exercise(17, "Pushup", "Chest", getString(R.string.pushups), R.drawable.pushup))
        exercises.add(Exercise(18, "One-Armed Medicine Ball Pushup", "Chest", getString(R.string.one_armed_medicine_ball_pushups), R.drawable.one_armed_medicine_ball_pushup))
        exercises.add(Exercise(19, "Medicine Ball Pushup Drop 'n' Pop", "Chest", getString(R.string.medicine_ball_pushups_drop_n_pop), R.drawable.drop_n_pop))
        exercises.add(Exercise(20, "Medicine Ball Crossover Pushup", "Chest", getString(R.string.medicine_ball_crossover_pushup), R.drawable.medicine_ball_crossover_pushup))
        exercises.add(Exercise(21, "Plyo Pushup", "Chest", getString(R.string.plyo_pushups), R.drawable.plyo_pushup))
        exercises.add(Exercise(22, "Wide-Grip Pushup", "Chest", getString(R.string.wide_grip_pushups), R.drawable.wide_grip_pushup))
        exercises.add(Exercise(23, "Band-Resisted Pushup with Feet Elevated", "Chest", getString(R.string.band_resisted_pushup_with_feet_elevated), R.drawable.band_resisted_pushup_w_feet_elevated))
        exercises.add(Exercise(24, "Band-Resisted Flies", "Chest", getString(R.string.band_resisted_flies), R.drawable.band_resisted_flies))
        exercises.add(Exercise(25, "Pec Deck", "Chest", getString(R.string.pec_deck), R.drawable.pec_deck))
        exercises.add(Exercise(26, "Wide-Grip Dips", "Chest", getString(R.string.wide_grip_dips), R.drawable.wide_grip_dips))
        exercises.add(Exercise(293, "High Cable Flies", "Chest", getString(R.string.high_cable_flies), R.drawable.high_cable_flies))

        val tricepsExercises = ArrayList<Exercise>()
        tricepsExercises.add(Exercise(27, "Triceps Extension", "Triceps", getString(R.string.triceps_extension), R.drawable.triceps_extensions))
        tricepsExercises.add(Exercise(28, "Close-Grip Pushup", "Triceps", getString(R.string.close_grip_pushup), R.drawable.close_grip_pushup))
        tricepsExercises.add(Exercise(29, "Close-Grip Bench Press", "Triceps", getString(R.string.close_grip_bench_press), R.drawable.close_grip_bench_press))
        tricepsExercises.add(Exercise(30, "Decline Triceps Extension", "Triceps", getString(R.string.decline_triceps_extension), R.drawable.decline_triceps_extensions))
        tricepsExercises.add(Exercise(31, "Band Pushdown", "Triceps", getString(R.string.band_pushdown), R.drawable.band_pushdown))
        tricepsExercises.add(Exercise(32, "Close-Grip Floor Press", "Triceps", getString(R.string.close_grip_floor_press), R.drawable.close_grip_floor_press))
        tricepsExercises.add(Exercise(33, "Lying Triceps Extension", "Triceps", getString(R.string.lying_triceps_extension), R.drawable.lying_triceps_extensions))
        tricepsExercises.add(Exercise(34, "Pullovers", "Triceps", getString(R.string.pullovers), R.drawable.pullover_triceps))
        tricepsExercises.add(Exercise(35, "Tate Press", "Triceps", getString(R.string.tate_press), R.drawable.tate_press))
        tricepsExercises.add(Exercise(36, "Underhand Kickback", "Triceps", getString(R.string.underhand_kickback), R.drawable.underhand_kickback))
        tricepsExercises.add(Exercise(37, "One-Arm Overhead Extension", "Triceps", getString(R.string.one_arm_overhead_extension), R.drawable.one_erm_overhead_extensions))
        tricepsExercises.add(Exercise(38, "Dip", "Triceps", getString(R.string.dip), R.drawable.dips))
        tricepsExercises.add(Exercise(39, "Band Triceps Extension", "Triceps", getString(R.string.band_triceps_extension), R.drawable.band_triceps_extension))
        tricepsExercises.add(Exercise(40, "Diamond Pushup", "Triceps", getString(R.string.diamond_pushup), R.drawable.diamond_pushup))
        tricepsExercises.add(Exercise(41, "Suspension Trainer Triceps Extension", "Triceps", getString(R.string.suspension_trainer_triceps_extension), R.drawable.suspension_trainer_triceps_extension))
        tricepsExercises.add(Exercise(282, "Skull Crushers", "Triceps", getString(R.string.skull_crushers), R.drawable.skull_crushers))
        tricepsExercises.add(Exercise(283, "Single Arm Cable Extension", "Triceps", getString(R.string.single_arm_cable_extension), R.drawable.single_arm_cable_extension))
        tricepsExercises.add(Exercise(284, "Reverse Grip Cable Extension", "Triceps", getString(R.string.reverse_grip_cable_extension), R.drawable.reverse_extension))
        tricepsExercises.add(Exercise(285, "Overhead Triceps Extension", "Triceps", getString(R.string.overhead_triceps_extension), R.drawable.dumbbell_triceps_extension))
        tricepsExercises.add(Exercise(294, "Triceps Pushdown", "Triceps", getString(R.string.triceps_pushdown), R.drawable.triceps_pushdowns))
        tricepsExercises.add(Exercise(295, "Cable Triceps Kickback", "Triceps", getString(R.string.cable_triceps_kickback), R.drawable.cable_triceps_kickback))

        val shouldersExercises = ArrayList<Exercise>()
        shouldersExercises.add(Exercise(42, "Renegade Row", "Shoulders", getString(R.string.renegade_row), R.drawable.renegade_row))
        shouldersExercises.add(Exercise(43, "Standing Dumbbell Flies", "Shoulders", getString(R.string.standing_dumbbell_flies), R.drawable.standing_dumbbell_flies))
        shouldersExercises.add(Exercise(44, "Face Pull", "Shoulders", getString(R.string.face_pulls), R.drawable.face_pull))
        shouldersExercises.add(Exercise(45, "High Pull", "Shoulders", getString(R.string.high_pull), R.drawable.high_pull))
        shouldersExercises.add(Exercise(46, "Seated Dumbbell Clean", "Shoulders", getString(R.string.seated_dumbbell_clean), R.drawable.seated_dumbbell_clean))
        shouldersExercises.add(Exercise(47, "Trap Raise", "Shoulders", getString(R.string.trap_raise), R.drawable.trap_raise))
        shouldersExercises.add(Exercise(48, "Clean and Press", "Shoulders", getString(R.string.clean_and_press), R.drawable.clean_and_press))
        shouldersExercises.add(Exercise(49, "Snatch-Grip High Pull", "Shoulders", getString(R.string.snatch_grip_high_pull), R.drawable.snatch_grip_high_pull))
        shouldersExercises.add(Exercise(50, "Band Lateral Raise", "Shoulders", getString(R.string.band_lateral_raise), R.drawable.band_lateral_raise))
        shouldersExercises.add(Exercise(51, "Band Front Raise", "Shoulders", getString(R.string.band_front_raise), R.drawable.band_front_raise))
        shouldersExercises.add(Exercise(52, "Band Bentover Lateral Raise", "Shoulders", getString(R.string.band_bentover_lateral_raise), R.drawable.band_bentover_lateral_raise))
        shouldersExercises.add(Exercise(53, "Band W Raise", "Shoulders", getString(R.string.band_w_raise), R.drawable.band_w_raise))
        shouldersExercises.add(Exercise(54, "Single-Arm Cable Row", "Shoulders", getString(R.string.single_arm_cable_row), R.drawable.single_arm_cable_row))
        shouldersExercises.add(Exercise(55, "Suspension Trainer Y-Raise", "Shoulders", getString(R.string.suspension_trainer_y_raise), R.drawable.suspension_trainer_y_raise))
        shouldersExercises.add(Exercise(56, "Suspension Trainer Rear-Delt Raise", "Shoulders", getString(R.string.suspension_trainer_rear_delts_raise), R.drawable.suspension_trainer_rear_delts_raise))
        shouldersExercises.add(Exercise(57, "Dive Bomber Pushup", "Shoulders", getString(R.string.dive_bomber_pushup), R.drawable.dive_bomber_pushup))
        shouldersExercises.add(Exercise(58, "Pike Press", "Shoulders", getString(R.string.pike_press), R.drawable.pike_press))
        shouldersExercises.add(Exercise(59, "Dip", "Shoulders", getString(R.string.dip_shoulders), R.drawable.dip_shoulders))
        shouldersExercises.add(Exercise(60, "Lateral Plank Walk", "Shoulders", getString(R.string.lateral_plank_walk), R.drawable.lateral_plank_walk))
        shouldersExercises.add(Exercise(61, "Dumbbell Neutral Grip Overhead Press", "Shoulders", getString(R.string.dumbbell_neutral_grip_overhead_press), R.drawable.dumbbell_neutral_grip_overhead_press))
        shouldersExercises.add(Exercise(62, "Dumbbell Raise Complex", "Shoulders", getString(R.string.dumbbell_raise_complex), R.drawable.dumbbell_raise_complex))
        shouldersExercises.add(Exercise(63, "Snatch-Grip Low Pull", "Shoulders", getString(R.string.snatch_grip_low_pull), R.drawable.snatch_grip_low_pull))
        shouldersExercises.add(Exercise(64, "Snatch-Grip Shrug Pull", "Shoulders", getString(R.string.snatch_grip_shrug_pull), R.drawable.snatch_grip_shrug_pull))
        shouldersExercises.add(Exercise(65, "Rack Deadlift", "Shoulders", getString(R.string.rack_deadlift), R.drawable.rack_deadlift))
        shouldersExercises.add(Exercise(66, "Farmer's Walk", "Shoulders", getString(R.string.farmers_walk), R.drawable.farmers_walk))
        shouldersExercises.add(Exercise(67, "Dumbbell Deadlift/Shrug Combo", "Shoulders", getString(R.string.dumbbell_deadlift_shrug_combo), R.drawable.dumbbell_deadlift_shrug_combo))
        shouldersExercises.add(Exercise(68, "Bottoms-up Kettlebell Press", "Shoulders", getString(R.string.bottoms_up_kettlebell_press), R.drawable.bottoms_up_kettlebell_press))
        shouldersExercises.add(Exercise(69, "Incline Bench Press", "Shoulders", getString(R.string.incline_bench_press), R.drawable.incline_bench_press))
        shouldersExercises.add(Exercise(70, "Machine Shoulder Press", "Shoulders", getString(R.string.machine_shoulder_press), R.drawable.machine_shoulder_press))
        shouldersExercises.add(Exercise(71, "Bentover Reverse Flies", "Shoulders", getString(R.string.bentover_reverse_flies), R.drawable.bentover_reverse_flies))
        shouldersExercises.add(Exercise(72, "Waiter’s Walk", "Shoulders", getString(R.string.waiters_walk), R.drawable.waiters_walk))
        shouldersExercises.add(Exercise(73, "Upright Row", "Shoulders", getString(R.string.upright_row), R.drawable.upright_row))
        shouldersExercises.add(Exercise(74, "Feet-Elevated Pushup", "Shoulders", getString(R.string.feet_elevated_pushup), R.drawable.feet_elevated_pushup))
        shouldersExercises.add(Exercise(75, "Suspension Trainer Knee Tuck", "Shoulders", getString(R.string.suspension_trainer_knee_tuck), R.drawable.suspension_trainer_knee_tuck))
        shouldersExercises.add(Exercise(76, "T Pushup", "Shoulders", getString(R.string.t_pushup), R.drawable.t_pushup))
        shouldersExercises.add(Exercise(77, "Dumbbell Incline Row", "Shoulders", getString(R.string.dumbbell_incline_row), R.drawable.dumbbell_incline_row))
        shouldersExercises.add(Exercise(78, "Offset Single-Arm Chest Press", "Shoulders", getString(R.string.offset_single_arm_chest_press), R.drawable.offset_single_arm_chest_press))
        shouldersExercises.add(Exercise(79, "Angled Press", "Shoulders", getString(R.string.angled_press), R.drawable.angled_press))
        shouldersExercises.add(Exercise(80, "Inverted Row", "Shoulders", getString(R.string.inverted_row), R.drawable.inverted_row))
        shouldersExercises.add(Exercise(81, "Barbell Overhead Press", "Shoulders", getString(R.string.barbell_overhead_press), R.drawable.barbell_overhead_press))
        shouldersExercises.add(Exercise(82, "Single-Arm Cable Front Raise", "Shoulders", getString(R.string.single_arm_cable_front_raise), R.drawable.single_arm_cable_front_raise))
        shouldersExercises.add(Exercise(83, "Cat-Cow", "Shoulders", getString(R.string.cat_cow), R.drawable.cat_cow))
        shouldersExercises.add(Exercise(84, "Suspension Trainer Pike Pushup", "Shoulders", getString(R.string.suspension_trainer_pike_pushup), R.drawable.suspension_trainer_pike_pushup))
        shouldersExercises.add(Exercise(85, "Pullup", "Shoulders", getString(R.string.pullup), R.drawable.pullup))
        shouldersExercises.add(Exercise(86, "Cross-Body Landmine Row", "Shoulders", getString(R.string.cross_body_landmine_row), R.drawable.cross_body_landmine_row))
        shouldersExercises.add(Exercise(87, "Seated Single-Arm Press", "Shoulders", getString(R.string.seated_single_arm_press), R.drawable.seated_single_arm_press))
        shouldersExercises.add(Exercise(88, "Dumbbell Bentover Lateral Raise", "Shoulders", getString(R.string.dumbbell_bentover_lateral_raise), R.drawable.dumbbell_bentover_lateral_raise))
        shouldersExercises.add(Exercise(89, "Suspension Trainer Row", "Shoulders", getString(R.string.suspension_trainer_row), R.drawable.suspension_trainer_row))
        shouldersExercises.add(Exercise(90, "Inchworm", "Shoulders", getString(R.string.inchworm), R.drawable.inchworm))
        shouldersExercises.add(Exercise(91, "Incline Bench-Supported Y Raise", "Shoulders", getString(R.string.incline_bench_press_y_raise), R.drawable.incline_bench_supported_y_raise))
        shouldersExercises.add(Exercise(287, "Dumbbell Lateral Raise", "Shoulders", getString(R.string.dumbbell_lateral_raise), R.drawable.dumbbell_lateral_raise))
        shouldersExercises.add(Exercise(288, "Dumbbell Front Raise", "Shoulders", getString(R.string.dumbbell_front_raise), R.drawable.dumbbell_front_raise))
        shouldersExercises.add(Exercise(289, "Seated Overhead Dumbbell Press", "Shoulders", getString(R.string.seated_overhead_dumbbell_press), R.drawable.seated_dumbbell_shoulder_press))

        val backToWorkoutButton : FloatingActionButton = findViewById(R.id.pushBackToWorkoutButton)
        backToWorkoutButton.backgroundTintList = ColorStateList.valueOf(color.getInt("color", Color.BLUE))
        backToWorkoutButton.setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }

        spinner.onItemSelectedListener = SpinnerActivity(recyclerView, switch, searchBar, exercises, tricepsExercises, shouldersExercises)


        switch.setOnCheckedChangeListener { _, isChecked ->
            position = spinner.selectedItemPosition
            when(position){
                0 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(exercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesAdapter(exercises, this, this)
                        switch.text = "Show Thumbnails"
                    }
                }
                1 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(tricepsExercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesAdapter(tricepsExercises, this, this)
                        switch.text = "Show Thumbnails"
                    }
                }
                2 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(shouldersExercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesWithImageAdapter(shouldersExercises, this, this)
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
                    if(!text.isNullOrBlank()){
                        for(i in exercises.indices){
                            val word = exercises[i].name.split("\\s".toRegex()).toTypedArray()
                            for(j in word.indices){
                                if(word[j].startsWith(text, true)){
                                    list.add(exercises[i])
                                }
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, exercises)
                    }
                }
                1 -> {
                    val list = ArrayList<Exercise>()
                    if(!text.isNullOrBlank()) {
                        for (i in tricepsExercises.indices) {
                            val word =
                                tricepsExercises[i].name.split("\\s".toRegex()).toTypedArray()
                            for (j in word.indices) {
                                if (word[j].startsWith(text, true)) {
                                    list.add(tricepsExercises[i])
                                }
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, tricepsExercises)
                    }
                }
                2 -> {
                    val list = ArrayList<Exercise>()
                    if(!text.isNullOrBlank()) {
                        for (i in shouldersExercises.indices) {
                            val word =
                                shouldersExercises[i].name.split("\\s".toRegex()).toTypedArray()
                            for (j in word.indices) {
                                if (word[j].startsWith(text, true)) {
                                    list.add(shouldersExercises[i])
                                }
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, shouldersExercises)
                    }
                }
            }
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