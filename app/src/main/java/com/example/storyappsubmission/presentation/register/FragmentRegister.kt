package com.example.storyappsubmission.presentation.register

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.storyappsubmission.R
import com.example.storyappsubmission.data.register.model.RegisterResponse
import com.example.storyappsubmission.databinding.FragmentRegisterBinding
import org.w3c.dom.Text


class FragmentRegister : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel : RegisterViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playAnimation()
        enableRegisterButton()
        val name = binding.edRegisterName.text
        val email = binding.edRegisterEmail.text
        val password = binding.edRegisterPassword.text

        binding.btnRegister.setOnClickListener {
            viewModel.userRegister(name.toString(), email.toString(), password.toString())
            viewModel.resultRegister.observe(viewLifecycleOwner){
                if (it.error){
                    Toast.makeText(view.context, it.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(view.context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_fragmentRegister_to_loginFragment)
                }
            }
            viewModel.isLoading.observe(viewLifecycleOwner){
                isLoading(it)
            }
        }

        binding.toolbarRegister.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivSignup, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val cardView = ObjectAnimator.ofFloat(binding.cardviewRegister,View.ALPHA, 1f).setDuration(500)
        val registerText = ObjectAnimator.ofFloat(binding.tvRegisterHere, View.ALPHA, 1f).setDuration(500)
        val edEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val edName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val edPassword= ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(cardView,registerText,edName,edEmail,edPassword, btnRegister)
            start()
        }
    }

    private fun isLoading(it: Boolean) {
        binding.progressBarRegister.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun enableRegisterButton() {
        binding.edRegisterName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               if (binding.edRegisterName.error == null ){
                   isEnable()
               }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edRegisterEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edRegisterEmail.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edRegisterPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edRegisterPassword.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
    fun isEnable() {
        val name = binding.edRegisterName.text
        val email = binding.edRegisterEmail.text
        val password = binding.edRegisterPassword.text
        binding.btnRegister.isEnabled =
            !name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}