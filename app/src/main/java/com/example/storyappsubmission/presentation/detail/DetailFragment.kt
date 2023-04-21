package com.example.storyappsubmission.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.storyappsubmission.R
import com.example.storyappsubmission.data.story.model.Story
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.storyappsubmission.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel : DetailViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        fetchData()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun fetchData() {
        val id = DetailFragmentArgs.fromBundle(arguments as Bundle).id
        viewModel.detailResult.observe(viewLifecycleOwner){
            setData(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
        viewModel.fetchDetail(id)
    }

    private fun isLoading(it: Boolean) {
        binding.proggresBarDetail.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setData(it: Story) {
        Glide.with(requireContext())
            .load(it.photoUrl)
            .into(binding.ivImageDetail)

        binding.tvUsernameDetail.text = it.name
        binding.tvDescriptionDetail.text = it.description
    }

}