package com.example.storyappsubmission.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappsubmission.R
import com.example.storyappsubmission.UserPreferencesViewModel
import com.example.storyappsubmission.data.story.model.ListStoryItem
import com.example.storyappsubmission.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModel()
    private val userPreferencesViewModel : UserPreferencesViewModel by viewModel()

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
        viewModel.fetchStory.observe(viewLifecycleOwner){
            setData(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
        viewModel.fetchStory()
        binding.toolbarHome.setOnMenuItemClickListener{ menuItem ->
            when(menuItem.itemId){
                R.id.btn_logout -> {
                    userPreferencesViewModel.clearUserData()
                    findNavController().navigate(R.id.action_fragmentHome_to_loginFragment)
                    true
                } else -> false
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun isLoading(it: Boolean) {
        binding.progressBarHome.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setData(storyUsers : List<ListStoryItem>){
        binding.rvUserStory.setHasFixedSize(true)
        binding.rvUserStory.layoutManager = LinearLayoutManager(requireContext())

        val adapter = HomeAdapter(storyUsers)
        adapter.setOnItemClickListener {
            val toDetailFragment = FragmentHomeDirections.actionFragmentHomeToDetailFragment()
            toDetailFragment.id = storyUsers[it].id
            view?.findNavController()?.navigate(toDetailFragment)
        }
        binding.rvUserStory.adapter = adapter
    }
}