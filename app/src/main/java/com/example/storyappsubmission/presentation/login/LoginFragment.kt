package com.example.storyappsubmission.presentation.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import com.example.storyappsubmission.R
import com.example.storyappsubmission.UserPreferencesViewModel
import com.example.storyappsubmission.data.local.UserModel
import com.example.storyappsubmission.data.login.model.LoginResult
import com.example.storyappsubmission.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModel()
    private val userViewModel : UserPreferencesViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playAnimation()
        enableButton()
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_fragmentRegister)
        }
        binding.btnLogin.setOnClickListener {
            viewModel.userLogin(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
        }
        viewModel.loginResult.observe(viewLifecycleOwner){
            if (it != null){
                setLoginState(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    private fun enableButton(){
        binding.edLoginEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edLoginEmail.error == null ){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edLoginPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edLoginPassword.error == null){
                    isEnable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.loginImage, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val cardView = ObjectAnimator.ofFloat(binding.cardViewLogin,View.ALPHA, 1f).setDuration(500)
        val loginText = ObjectAnimator.ofFloat(binding.wellcome,View.ALPHA, 1f).setDuration(500)
        val edtEmail = ObjectAnimator.ofFloat(binding.edLoginEmail,View.ALPHA, 1f).setDuration(500)
        val edtPassword = ObjectAnimator.ofFloat(binding.edLoginPassword,View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin,View.ALPHA, 1f).setDuration(500)
        val tvAccount = ObjectAnimator.ofFloat(binding.account,View.ALPHA, 1f).setDuration(500)
        val toRegist = ObjectAnimator.ofFloat(binding.tvRegister,View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(tvAccount, toRegist)
        }
        
        AnimatorSet().apply { 
            playSequentially(cardView, loginText,edtEmail,edtPassword,btnLogin,together)
            start()
        }
    }

    private fun setLoginState(loginResult : LoginResult){
        val userModel = UserModel(loginResult.userId, loginResult.name, loginResult.token)
        userViewModel.saveUserData(userModel)
        Toast.makeText(requireContext(), "Welcome ${userModel.name}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_loginFragment_to_fragmentHome)

    }
    private fun isLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !it
    }
    private fun isEnable(){
        val email = binding.edLoginEmail.text
        val password = binding.edLoginPassword.text

        binding.btnLogin.isEnabled = !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}