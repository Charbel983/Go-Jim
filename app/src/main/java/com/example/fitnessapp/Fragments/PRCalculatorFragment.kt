package com.example.fitnessapp.Fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import com.example.fitnessapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [PRCalculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PRCalculatorFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val parentHolder = inflater.inflate(R.layout.fragment_p_r_calculator, container, false)
        val radioGroup : RadioGroup = parentHolder.findViewById(R.id.prCalculatorRadioGroup)
        val title : TextView = parentHolder.findViewById(R.id.prCalculatorTitleTextView)
        val button : Button = parentHolder.findViewById(R.id.prCalculatorButton)
        val weight : EditText = parentHolder.findViewById(R.id.prCalculatorWeightEditText)
        val reps : EditText = parentHolder.findViewById(R.id.prCalculatorRepsEditText)
        val result : TextView = parentHolder.findViewById(R.id.prCalculatorResultTextView)
        val color = requireContext().getSharedPreferences("Theme", Context.MODE_PRIVATE)
        title.setTextColor(color.getInt("color", 0))
        button.setBackgroundColor(color.getInt("color", Color.BLUE))
        val radioButton : RadioButton = parentHolder.findViewById(R.id.kgRadioButton)
        radioButton.isChecked = true

        var unit = "kg"

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lbsRadioButton -> unit = "lbs"
                R.id.kgRadioButton -> unit = "kg"
            }
        }

        button.setOnClickListener {
            when {
                weight.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show()
                    weight.error = "Please fill this field!"
                    weight.backgroundTintList = ColorStateList.valueOf(Color.RED)
                }
                reps.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show()
                    reps.error = "Please fill this field!"
                    reps.backgroundTintList = ColorStateList.valueOf(Color.RED)
                }
                else -> {
                    val res = (0.033 * Integer.parseInt(weight.text.toString()) * Integer.parseInt(reps.text.toString())) + Integer.parseInt(weight.text.toString())
                    var pr = 0.0
                    val comparator = res.toInt() + 0.5
                    pr = when {
                        res > comparator -> (res.toInt() + 1).toDouble()
                        res == comparator -> comparator
                        else -> res.toInt().toDouble()
                    }
                    result.text = "Your 1 Rep Max should be around : $pr $unit"
                }
            }
        }

        return parentHolder
    }

    private fun getUnit(radioGroup: RadioGroup, view: View) : String {
        var unit = ""
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.lbsRadioButton -> {
                    unit = "LBS"
                }
                R.id.kgRadioButton -> {
                    unit = "KG"
                }
            }
        }
        return unit
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PRCalculatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PRCalculatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}