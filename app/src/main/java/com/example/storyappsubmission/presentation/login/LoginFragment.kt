package com.example.storyappsubmission.presentation.login

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
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_fragmentRegister)
        }
        setEnable()
        binding.btnLogin.setOnClickListener {
            viewModel.userLogin(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
        }
        viewModel.loginResult.observe(viewLifecycleOwner){
            setLoginState(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setLoginState(loginResult : LoginResult?){
        if (loginResult == null){
            Toast.makeText(context, "User Not Found", Toast.LENGTH_SHORT).show()
        }else {
            val userModel = UserModel(loginResult.userId, loginResult.name, loginResult.token)
            userViewModel.saveUserData(userModel)
            
            findNavController().navigate(R.id.action_loginFragment_to_fragmentHome)
        }
    }
    private fun isLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.GONE else View.VISIBLE
    }

    private fun setEnable() {
        binding.edLoginEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when{
                    p0.isNullOrEmpty() -> binding.edLoginEmail.error = "Email Tidak Boleh Kosong"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(p0).matches() -> binding.edLoginEmail.error = "Email tidak valid"
                    else->{
                        binding.edLoginEmail.error = null
                        isEnable()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edLoginPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when{
                    p0.isNullOrEmpty() -> binding.edLoginPassword.error = "Password tidak boleh kosong"
                    p0.length < 8 -> binding.edLoginPassword.error = "password minimal 8 karakter"
                    else -> {
                        binding.edLoginPassword.error = null
                        isEnable()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun isEnable(){
        val email = binding.edLoginEmail.text
        val password = binding.edLoginPassword.text

        binding.btnLogin.isEnabled = email != null && password !=null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}