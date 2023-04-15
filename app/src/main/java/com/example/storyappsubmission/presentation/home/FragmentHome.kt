package com.example.storyappsubmission.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.storyappsubmission.R
import com.example.storyappsubmission.UserPreferencesViewModel
import com.example.storyappsubmission.databinding.FragmentHomeBinding
import com.example.storyappsubmission.di.viewModelModule
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_addStoryFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

}