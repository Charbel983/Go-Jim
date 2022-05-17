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

class PullExercisesActivity : AppCompatActivity() {

    private var position = 0
    private lateinit var mAdView : AdManagerAdView
    private val activity = this

    class SpinnerActivity(private val recyclerView: RecyclerView, private val switch: Switch,
                          private val searchBar : EditText,
                          private val backExercises : List<Exercise>,
                          private val bicepsExercises : List<Exercise>,
                          private val forearmsExercises : List<Exercise>) : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            when {
                parent.getItemAtPosition(pos).equals("Back") -> {
                    PullExercisesActivity().showData(recyclerView, switch, backExercises)
                    searchBar.clearFocus()
                    hideSoftKeyboard()
                }
                parent.getItemAtPosition(pos).equals("Biceps") -> {
                    PullExercisesActivity().showData(recyclerView, switch, bicepsExercises)
                    searchBar.clearFocus()
                    hideSoftKeyboard()
                }
                parent.getItemAtPosition(pos).equals("Forearms") -> {
                    PullExercisesActivity().showData(recyclerView, switch, forearmsExercises)
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
        setContentView(R.layout.activity_pull_exercises)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        val spinner : Spinner = findViewById(R.id.pullExercisesSpinner)
        val title : TextView = findViewById(R.id.pullExercisesTitle)
        val color = getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val switch : Switch = findViewById(R.id.pullExercisesSwitch)
        val recyclerView : RecyclerView = findViewById(R.id.pullExercisesRecyclerView)
        val searchBar : EditText = findViewById(R.id.pullExercisesSearchEditText)
        val searchImage : ImageView = findViewById(R.id.pullExercisesSearchImageView)
        val customBackground = getSharedPreferences("Background", Context.MODE_PRIVATE)
        val layout : ConstraintLayout = findViewById(R.id.pullExercisesLayout)
        val image = customBackground.getString("customBackground", null)
        if(image != null){
            layout.background = BitmapDrawable(resources, loadPhoto(image)?.bmp)
        }

        val backExercises = ArrayList<Exercise>()
        backExercises.add(Exercise(92, "Deadlift", "Back", getString(R.string.deadlift), R.drawable.deadlift))
        backExercises.add(Exercise(93, "Weighted Pullup", "Back", getString(R.string.weighted_pullup), R.drawable.weighted_pullup))
        backExercises.add(Exercise(94, "Dumbbell Row", "Back", getString(R.string.dumbbell_row), R.drawable.dumbbell_row))
        backExercises.add(Exercise(95, "Thread the Needle", "Back", getString(R.string.thread_the_needle), R.drawable.thread_the_needle))
        backExercises.add(Exercise(96, "Incline Dumbbell Row", "Back", getString(R.string.incline_dumbbell_row), R.drawable.incline_dumbbell_row))
        backExercises.add(Exercise(97, "Chinup", "Back", getString(R.string.chinup), R.drawable.chinup))
        backExercises.add(Exercise(98, "Y-W-T Holds", "Back", getString(R.string.y_w_t_holds), R.drawable.y_w_t_row))
        backExercises.add(Exercise(99, "Alternating Dumbbell Row", "Back", getString(R.string.alternating_dumbbell_row), R.drawable.alternating_dumbbell_row))
        backExercises.add(Exercise(100, "Inverted Row", "Back", getString(R.string.inverted_row_back), R.drawable.inverted_row_back))
        backExercises.add(Exercise(101, "Snatch-Grip Rack Deadlift", "Back", getString(R.string.snatch_grip_rack_deadlift), R.drawable.snatch_grip_rack_deadlift))
        backExercises.add(Exercise(102, "Swiss Ball Leg Curl", "Back", getString(R.string.swiss_ball_leg_curl), R.drawable.swiss_ball_leg_curl))
        backExercises.add(Exercise(103, "Wide-Grip Pullup", "Back", getString(R.string.wide_grip_pullup), R.drawable.wide_grip_pullup))
        backExercises.add(Exercise(104, "Landmine One-Arm Row", "Back", getString(R.string.landmine_one_arm_row), R.drawable.landmine_one_arm_row))
        backExercises.add(Exercise(105, "Towel Cable Row", "Back", getString(R.string.towel_cable_row), R.drawable.towel_cable_row))
        backExercises.add(Exercise(106, "Rotational Inverted Row", "Back", getString(R.string.rotational_inverted_row), R.drawable.rotational_inverted_row))
        backExercises.add(Exercise(107, "Burpee", "Back", getString(R.string.burpee), R.drawable.burpee))
        backExercises.add(Exercise(108, "Pike-Up to Superman", "Back", getString(R.string.pike_up_to_superman), R.drawable.pike_up_to_superman))
        backExercises.add(Exercise(109, "Lying Lateral Raise", "Back", getString(R.string.lying_lateral_raise), R.drawable.lying_lateral_raise))
        backExercises.add(Exercise(110, "Hang Clean", "Back", getString(R.string.hang_clean), R.drawable.hang_clean))
        backExercises.add(Exercise(111, "Back Extension", "Back", getString(R.string.back_extension), R.drawable.back_extension))
        backExercises.add(Exercise(112, "Seated Cable Row", "Back", getString(R.string.seated_cable_row), R.drawable.seated_cable_row))
        backExercises.add(Exercise(113, "Bentover Reverse Flies", "Back", getString(R.string.bentover_reverse_flies_back), R.drawable.bentover_reverse_flies))
        backExercises.add(Exercise(114, "Aquaman", "Back", getString(R.string.aquaman), R.drawable.aquaman))
        backExercises.add(Exercise(115, "Superman", "Back", getString(R.string.superman), R.drawable.superman))
        backExercises.add(Exercise(116, "Suspension Trainer Chinup", "Back", getString(R.string.suspension_trainer_chinup), R.drawable.suspension_trainer_chinup))
        backExercises.add(Exercise(117, "Barbell Back Squat", "Back", getString(R.string.barbell_back_squat), R.drawable.barbell_back_squat))
        backExercises.add(Exercise(118, "Suspension Trainer Inverted Row", "Back", getString(R.string.suspension_trainer_inverted_row), R.drawable.suspension_trainer_inverted_row))
        backExercises.add(Exercise(119, "Swiss Ball Reverse Back Extension", "Back", getString(R.string.swiss_ball_reverse_back_extension), R.drawable.swiss_ball_reverse_back_extension))
        backExercises.add(Exercise(120, "Yates Row", "Back", getString(R.string.yates_row), R.drawable.yates_row))
        backExercises.add(Exercise(121, "Bentover Row to Neck", "Back", getString(R.string.bentover_row_to_neck), R.drawable.bentover_row_to_neck))
        backExercises.add(Exercise(122, "Cat-Cow", "Back", getString(R.string.cat_cow), R.drawable.cat_cow))
        backExercises.add(Exercise(123, "Sled Pull", "Back", getString(R.string.sled_pull), R.drawable.sled_pull))
        backExercises.add(Exercise(124, "Dumbbell Romanian Deadlift", "Back", getString(R.string.dumbbell_romanian_deadlift), R.drawable.dumbbell_romanian_deadlift))
        backExercises.add(Exercise(125, "Lat Pulldown", "Back", getString(R.string.lat_pulldown), R.drawable.lat_pulldown))
        backExercises.add(Exercise(126, "One-Arm, One-Leg Plank", "Back", getString(R.string.one_arm_one_leg_plank), R.drawable.one_arm_one_leg_plank))
        backExercises.add(Exercise(127, "Kettlebell Swing", "Back", getString(R.string.kettlebell_swing), R.drawable.kettlebell_swing))
        backExercises.add(Exercise(128, "One-Leg Romanian Deadlift", "Back", getString(R.string.one_leg_romanian_deadlift), R.drawable.one_leg_romanian_deadlift))
        backExercises.add(Exercise(129, "Med Ball Slam", "Back", getString(R.string.med_ball_slam), R.drawable.med_ball_slam))
        backExercises.add(Exercise(130, "Medicine Ball Rotational Throw", "Back", getString(R.string.medicine_ball_rotational_throw), R.drawable.medicine_ball_rotational_throw))
        backExercises.add(Exercise(131, "Renegade Row", "Back", getString(R.string.renegade_row_back), R.drawable.renegade_row))
        backExercises.add(Exercise(132, "Seated Rope Pull", "Back", getString(R.string.seated_rope_pull), R.drawable.seated_rope_pull))
        backExercises.add(Exercise(133, "Farmer's Carry", "Back", getString(R.string.farmers_carry), R.drawable.farmers_carry))
        backExercises.add(Exercise(134, "Kettlebell Windmill", "Back", getString(R.string.kettlebell_windmill), R.drawable.kettlebell_windmill))
        backExercises.add(Exercise(135, "Pallof Press Iso Hold", "Back", getString(R.string.pallof_press_iso_hold), R.drawable.pallof_press_iso_hold))
        backExercises.add(Exercise(136, "Upright Row", "Back", getString(R.string.upright_row_back), R.drawable.upright_row))
        backExercises.add(Exercise(137, "T-Spine Rotation", "Back", getString(R.string.t_spine_rotation), R.drawable.t_spine_rotation))
        backExercises.add(Exercise(138, "Trap-Bar Deadlift", "Back", getString(R.string.trap_bar_deadlift), R.drawable.trap_bar_deadlift))
        backExercises.add(Exercise(139, "Reverse Cable Flies", "Back", getString(R.string.reverse_cable_flies), R.drawable.reverse_cable_flies))
        backExercises.add(Exercise(140, "Bird Dog Heavy Row", "Back", getString(R.string.bird_dog_heavy_row), R.drawable.bird_dog_heavy_row))
        backExercises.add(Exercise(141, "Back Extension (Cobra)", "Back", getString(R.string.back_extension_cobra), R.drawable.back_extension_cobra))
        backExercises.add(Exercise(286, "Close-Grip Lat Pulldown", "Back", getString(R.string.close_grip_lat_pulldown), R.drawable.close_grip_lat_pulldown))
        backExercises.add(Exercise(289, "Barbell Row", "Back", getString(R.string.barbell_row), R.drawable.barbell_row))
        backExercises.add(Exercise(290, "T-Bar Row", "Back", getString(R.string.t_bar_row), R.drawable.t_bar_row))
        backExercises.add(Exercise(291, "Wide-Grip Seated Cable Row", "Back", getString(R.string.wide_grip_seated_cable_row), R.drawable.wide_grip_seated_cable_row))

        val bicepsExercises = ArrayList<Exercise>()
        bicepsExercises.add(Exercise(142, "Fat-Grip Hammer Curl", "Biceps", getString(R.string.fat_grip_hammer_curl), R.drawable.fat_grip_hammer_curl))
        bicepsExercises.add(Exercise(143, "Behind-the-Back Cable Curl", "Biceps", getString(R.string.behind_the_back_cable_curl), R.drawable.behind_the_back_cable_curl))
        bicepsExercises.add(Exercise(144, "EZ-Bar Preacher Curl", "Biceps", getString(R.string.ez_bar_preacher_curl), R.drawable.ez_bar_preacher_curl))
        bicepsExercises.add(Exercise(145, "Reverse Curl", "Biceps", getString(R.string.reverse_curl), R.drawable.reverse_curl))
        bicepsExercises.add(Exercise(146, "Wide-Grip Curl", "Biceps", getString(R.string.wide_grip_curl), R.drawable.wide_grip_curl))
        bicepsExercises.add(Exercise(147, "Close-Grip Curl", "Biceps", getString(R.string.close_grip_curl), R.drawable.close_grip_curl))
        bicepsExercises.add(Exercise(148, "Conventional Barbell Curl", "Biceps", getString(R.string.conventional_barbell_curl), R.drawable.conventional_barbell_curl))
        bicepsExercises.add(Exercise(149, "Dumbbell Curl", "Biceps", getString(R.string.dumbbell_curl), R.drawable.dumbbell_curl))
        bicepsExercises.add(Exercise(150, "Drag Curl", "Biceps", getString(R.string.drag_curl), R.drawable.drag_curl))
        bicepsExercises.add(Exercise(151, "Hammer Curl", "Biceps", getString(R.string.hammer_curl), R.drawable.hammer_curl))
        bicepsExercises.add(Exercise(152, "Cheat Curl", "Biceps", getString(R.string.cheat_curl), R.drawable.cheat_curl))
        bicepsExercises.add(Exercise(153, "Band Curl", "Biceps", getString(R.string.band_curl), R.drawable.band_curl))
        bicepsExercises.add(Exercise(154, "Side Curl", "Biceps", getString(R.string.side_curl), R.drawable.side_curl))
        bicepsExercises.add(Exercise(155, "Reverse Curl", "Biceps", getString(R.string.reverse_curl), R.drawable.reverse_curl_2))
        bicepsExercises.add(Exercise(156, "Suspension Trainer Biceps Curl", "Biceps", getString(R.string.suspension_trainer_biceps_curl), R.drawable.suspension_trainer_biceps_curl))

        val forearmsExercises = ArrayList<Exercise>()
        forearmsExercises.add(Exercise(157, "Behind-the-Back Cable Curl", "Forearms", getString(R.string.behind_the_back_cable_curl_forearms), R.drawable.behind_the_back_cable_curl))
        forearmsExercises.add(Exercise(158, "EZ-Bar Preacher Curl", "Forearms", getString(R.string.ez_bar_preacher_curl_forearms), R.drawable.ez_bar_preacher_curl))
        forearmsExercises.add(Exercise(159, "Reverse Curl", "Forearms", getString(R.string.reverse_curl_forearms), R.drawable.reverse_curl))
        forearmsExercises.add(Exercise(160, "Wide Grip Curl", "Forearms", getString(R.string.wide_grip_curl_forearms), R.drawable.wide_grip_curl))
        forearmsExercises.add(Exercise(161, "Towel Cable Row", "Forearms", getString(R.string.towel_cable_row_forearms), R.drawable.towel_cable_row))
        forearmsExercises.add(Exercise(162, "Towel Pullup", "Forearms", getString(R.string.towel_pullup), R.drawable.towel_pullup))
        forearmsExercises.add(Exercise(163, "Towel Row-to-Chest", "Forearms", getString(R.string.towel_row_to_chest), R.drawable.towel_row_to_chest))
        forearmsExercises.add(Exercise(164, "Towel Kettlebell Curl", "Forearms", getString(R.string.towel_kettlebell_curl), R.drawable.towel_kettlebell_curl))
        forearmsExercises.add(Exercise(165, "Farmer's Walk", "Forearms", getString(R.string.farmers_walk_forearms), R.drawable.farmers_walk))
        forearmsExercises.add(Exercise(166, "Hammer Cheat Curl", "Forearms", getString(R.string.hammer_cheat_curl), R.drawable.hammer_cheat_curl))
        forearmsExercises.add(Exercise(167, "Wrist Curl", "Forearms", getString(R.string.wrist_curl), R.drawable.wrist_curl))
        forearmsExercises.add(Exercise(168, "Reverse Wrist Curl", "Forearms", getString(R.string.reverse_wrist_curl), R.drawable.reverse_wrist_curl))
        forearmsExercises.add(Exercise(169, "Grip Crush", "Forearms", getString(R.string.grip_crush), R.drawable.grip_crush))
        forearmsExercises.add(Exercise(170, "Towel Curl", "Forearms", getString(R.string.towel_curl), R.drawable.towel_curl))
        forearmsExercises.add(Exercise(171, "Reverse Curl 21", "Forearms", getString(R.string.reverse_curl_21), R.drawable.reverse_curl_21))
        forearmsExercises.add(Exercise(172, "Zottman Curl", "Forearms", getString(R.string.zottman_curl), R.drawable.zottman_curl))
        forearmsExercises.add(Exercise(173, "Drag Curl", "Forearms", getString(R.string.drag_curl_forearms), R.drawable.drag_curl_2))
        forearmsExercises.add(Exercise(174, "Chinup", "Forearms", getString(R.string.chinup_forearms), R.drawable.chinup))
        forearmsExercises.add(Exercise(175, "Parallel Bar Hand Walk", "Forearms", getString(R.string.parallel_bar_hand_walk), R.drawable.parallel_bar_hand_walk))
        forearmsExercises.add(Exercise(176, "Crab Walk", "Forearms", getString(R.string.crab_walk), R.drawable.crab_walk))
        forearmsExercises.add(Exercise(177, "Bar Hang", "Forearms", getString(R.string.bar_hang), R.drawable.bar_hang))
        forearmsExercises.add(Exercise(178, "Wrist Roller", "Forearms", getString(R.string.wrist_roller), R.drawable.wrist_roller))
        forearmsExercises.add(Exercise(179, "Rope Climbs", "Forearms", getString(R.string.rope_climbs), R.drawable.rope_climb))
        forearmsExercises.add(Exercise(180, "One-Arm Bottoms-Up Kettlebell Press", "Forearms", getString(R.string.one_arm_bottoms_up_kettlebell_press), R.drawable.one_arm_bottoms_up_kettlebell_press))

        searchImage.setOnClickListener {
            searchBar.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
        }

        window?.statusBarColor = color.getInt("color", Color.BLUE)
        actionbar?.setBackgroundDrawable(ColorDrawable(color.getInt("color", Color.BLUE)))
        title.setTextColor(color.getInt("color", Color.BLUE))
        ArrayAdapter.createFromResource(this, R.array.pullExercisesArray, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val backToWorkoutButton : FloatingActionButton = findViewById(R.id.pullBackToWorkoutButton)
        backToWorkoutButton.backgroundTintList = ColorStateList.valueOf(color.getInt("color", Color.BLUE))
        backToWorkoutButton.setOnClickListener {
            startActivity(Intent(this, WorkoutActivity::class.java))
        }

        spinner.onItemSelectedListener = SpinnerActivity(recyclerView, switch, searchBar,
            backExercises,
            bicepsExercises,
            forearmsExercises
        )


        switch.setOnCheckedChangeListener { _, isChecked ->
            position = spinner.selectedItemPosition
            when(position){
                0 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(backExercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesAdapter(backExercises, this, this)
                        switch.text = "Show Thumbnails"
                    }
                }
                1 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(bicepsExercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesAdapter(bicepsExercises, this, this)
                        switch.text = "Show Thumbnails"
                    }
                }
                2 -> {
                    if(isChecked){
                        recyclerView.adapter = ExercisesWithImageAdapter(forearmsExercises, this, this)
                        switch.text = "Hide Thumbnails"
                    }else{
                        recyclerView.adapter = ExercisesWithImageAdapter(forearmsExercises, this, this)
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
                        for(i in backExercises.indices){
                            val word = backExercises[i].name.split("\\s".toRegex()).toTypedArray()
                            for(j in word.indices){
                                if(word[j].startsWith(text, true)){
                                    list.add(backExercises[i])
                                }
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, backExercises)
                    }
                }
                1 -> {
                    val list = ArrayList<Exercise>()
                    if(!text.isNullOrBlank()) {
                        for (i in bicepsExercises.indices) {
                            val word = bicepsExercises[i].name.split("\\s".toRegex()).toTypedArray()
                            for (j in word.indices) {
                                if (word[j].startsWith(text, true)) {
                                    list.add(bicepsExercises[i])
                                }
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, bicepsExercises)
                    }
                }
                2 -> {
                    val list = ArrayList<Exercise>()
                    if(!text.isNullOrBlank()) {
                        for (i in forearmsExercises.indices) {
                            val word =
                                forearmsExercises[i].name.split("\\s".toRegex()).toTypedArray()
                            for (j in word.indices) {
                                if (word[j].startsWith(text, true)) {
                                    list.add(forearmsExercises[i])
                                }
                            }
                        }
                        showData(recyclerView, switch, list)
                    }else{
                        showData(recyclerView, switch, forearmsExercises)
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