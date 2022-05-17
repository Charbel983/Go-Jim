package com.example.fitnessapp.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnessapp.Activities.MainActivity
import com.example.fitnessapp.R
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var selectedImage : Bitmap? = null
    private lateinit var backgroundSharedPreferences : SharedPreferences
    private lateinit var backgroundNumber : SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var title : TextView
    private lateinit var setColorButton : Button
    private lateinit var customBackgroundButton : Button
    private lateinit var clearBackgroundButton : Button
    private lateinit var actionBar: ActionBar
    private var color = 0
    private var number  = 0
    private val imageLoader = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){
            selectedImage = getCapturedImage(it)
            number = backgroundNumber.getInt("number", 0)
            backgroundNumber.edit().putInt("number", number+1).apply()
            backgroundSharedPreferences.edit().putString("customBackground", "customBackground${number+1}").apply()
            savePhoto("customBackground${number+1}", selectedImage!!)
            activity?.startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val parentHolder = inflater.inflate(R.layout.fragment_settings, container, false)
        sharedPreferences = requireContext().getSharedPreferences("Theme", Context.MODE_PRIVATE)
        title = parentHolder.findViewById(R.id.settingsTextView)
        title.setTextColor(sharedPreferences.getInt("color", Color.BLUE))
        customBackgroundButton = parentHolder.findViewById(R.id.settingsCustomBackgroundButton)
        clearBackgroundButton = parentHolder.findViewById(R.id.settingsClearBackgroundButton)
        setColorButton = parentHolder.findViewById(R.id.setColorButton)
        actionBar = (requireActivity() as AppCompatActivity?)!!.supportActionBar!!
        color = sharedPreferences.getInt("color", Color.BLUE)
        setColorButton.setBackgroundColor(color)
        clearBackgroundButton.setBackgroundColor(color)
        customBackgroundButton.setBackgroundColor(color)
        backgroundSharedPreferences = requireContext().getSharedPreferences("Background", Context.MODE_PRIVATE)
        backgroundNumber = requireContext().getSharedPreferences("Number", Context.MODE_PRIVATE)

        customBackgroundButton.setOnClickListener {
            imageLoader.launch("image/*")
        }

        clearBackgroundButton.setOnClickListener {
            backgroundSharedPreferences.edit().putString("customBackground", null).apply()
            requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
            deletePhoto("customBackground$backgroundNumber")
        }

        setColorButton.setOnClickListener {
            openColorPicker()
        }

        return parentHolder
    }

    private fun savePhoto(filename : String, bmp : Bitmap) : Boolean {
        return try {
            requireActivity().openFileOutput("$filename.jpg", AppCompatActivity.MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)){
                    throw IOException("Couldn't save bmp")
                }
            }
            true
        }catch(e : IOException){
            e.printStackTrace()
            false
        }
    }

    private fun getCapturedImage(selectedPhotoUri: Uri): Bitmap {
        val bitmap = when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                selectedPhotoUri
            )
            else -> {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, selectedPhotoUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
        return bitmap
    }

    private fun openColorPicker(){
        val picker = AmbilWarnaDialog(requireContext(), color, object : AmbilWarnaDialog.OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog){
            }
            override fun onOk(dialog: AmbilWarnaDialog, color: Int){
                SettingsFragment().color = color
                title.setTextColor(color)
                customBackgroundButton.setBackgroundColor(color)
                clearBackgroundButton.setBackgroundColor(color)
                setColorButton.setBackgroundColor(color)
                actionBar.setBackgroundDrawable(ColorDrawable(color))
                sharedPreferences.edit().putInt("color", color).apply()
                requireActivity().window.statusBarColor = color
            }
        })
        picker.show()
    }

    private fun deletePhoto(filename: String) : Boolean {
        return try{
            requireContext().deleteFile(filename)
        }catch(e : Exception){
            false
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}