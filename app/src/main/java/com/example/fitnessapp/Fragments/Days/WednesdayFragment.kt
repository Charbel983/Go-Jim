package com.example.fitnessapp.Fragments.Days

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fitnessapp.Controllers.AllDaysFragmentController
import com.example.fitnessapp.R
import com.github.clans.fab.FloatingActionMenu

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [WednesdayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WednesdayFragment : Fragment() {

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    private lateinit var recyclerView : RecyclerView

    // TODO: Rename and change types of parameters
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

        val parentHolder = inflater.inflate(R.layout.fragment_wednesday, container, false)
        val addExercisesButton : FloatingActionMenu = parentHolder.findViewById(R.id.addExerciseButton)
        recyclerView = parentHolder.findViewById(R.id.wednesdayMainRecyclerView)
        val spinner : Spinner = parentHolder.findViewById(R.id.chosenWorkoutSpinner)
        val swipeRefreshLayout : SwipeRefreshLayout = parentHolder.findViewById(R.id.wednesdaySwipeRefreshLayout)
        val controller = AllDaysFragmentController("Wednesday", addExercisesButton, recyclerView, spinner, swipeRefreshLayout, requireActivity(), requireContext())
        controller.start()

        return parentHolder
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WednesdayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WednesdayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}