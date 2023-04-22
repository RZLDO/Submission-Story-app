package com.example.storyappsubmission.presentation.register

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


        super.onViewCreated(view, savedInstanceState)
    }

    private fun playAnimation() {

    }

    private fun isLoading(it: Boolean) {
        binding.progressBarRegister.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun enableRegisterButton() {
        binding.edRegisterName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when{
                    p0.isNullOrEmpty() -> binding.edRegisterName.error = "Nama Tidak Boleh Kosong"
                    else -> {
                        binding.edRegisterName.error = null
                        isEnable()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edRegisterEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when{
                    p0.isNullOrEmpty() -> binding.edRegisterEmail.error = "Email tidak boleh kosong"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(p0).matches() -> binding.edRegisterEmail.error = "Email tidak Valid"
                    else -> {
                        binding.edRegisterEmail.error = null
                        isEnable()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edRegisterPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when{
                    p0.isNullOrEmpty() -> binding.edRegisterPassword.error = "Password Tidak Boleh kosong"
                    p0.length < 8 -> binding.edRegisterPassword.error = "password minimal 8 karakter"
                    else ->{
                        binding.edRegisterPassword.error = null
                        isEnable()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun isEnable() {
        val name = binding.edRegisterName.text
        val email = binding.edRegisterEmail.text
        val password = binding.edRegisterPassword.text
        binding.btnRegister.isEnabled =
            !name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }


}