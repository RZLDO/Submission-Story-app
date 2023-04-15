package com.example.storyappsubmission.presentation.addStory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.Manifest.permission.CAMERA
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.storyappsubmission.databinding.FragmentAddStoryBinding
import com.example.storyappsubmission.utils.createCustomTempFile
import java.io.File

class AddStoryFragment : Fragment() {
    private var _binding : FragmentAddStoryBinding? = null
    private val binding get() = _binding!!
    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 1
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCamera.setOnClickListener{
            val cameraPermission = ContextCompat.checkSelfPermission(requireContext(), CAMERA)
            if (cameraPermission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(CAMERA), REQUEST_CAMERA_PERMISSION)
            }else{
                startTakePhoto()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun startTakePhoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoUri : Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.storyappsubmission",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)

        }
    }
    private lateinit var currentPhotoPath:String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK){
            val myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile.path)

            binding.ivAddImage.setImageBitmap(result)
        }


    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTakePhoto()
            } else {
                Toast.makeText(requireContext(), "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}