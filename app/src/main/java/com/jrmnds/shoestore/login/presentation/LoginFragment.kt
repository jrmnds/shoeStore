package com.jrmnds.shoestore.login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentLoginBinding
import com.jrmnds.shoestore.login.model.LoginModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configureViewModel()
        setBinders(inflater, container)
        observeValidations()
        observeFields()
        return binding.root
    }

    private fun setBinders(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        binding.lifecycleOwner = this
        (activity as AppCompatActivity).supportActionBar?.title = "Login"
    }

    private fun configureViewModel() {
        loginViewModelFactory = LoginViewModelFactory()
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }


    private fun observeFields() {
        binding.loginButton.setOnClickListener {
            setLoginData()
            loginViewModel.validateFields()
        }

        binding.createAccountButtonId.setOnClickListener {
            setLoginData()
            loginViewModel.validateFields()
        }

        loginViewModel.shouldCallNextPage.observe(viewLifecycleOwner){ shouldCallNextPage ->
            if(shouldCallNextPage){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragment3ToWelcomeFragment())
            }
        }
    }

    private fun setLoginData() {
        loginViewModel.setData(
            LoginModel(
                binding.emailId.text.toString(),
                binding.passwordId.text.toString()
            )
        )
    }

    private fun observeValidations(){
        loginViewModel.isEmailValid.observe(viewLifecycleOwner) { isValid ->
            when {
                !isValid -> {
                    binding.emailId.error = getString(R.string.invalid_email)
                    binding.emailId.requestFocus()
                }
                else -> {
                    loginViewModel.shouldGoToTheNextPage()
                }
            }
        }

        loginViewModel.isPasswordNotEmpty.observe(viewLifecycleOwner) { isPasswordValid ->
            when {
                !isPasswordValid -> {
                    binding.passwordId.error = getString(R.string.invalid_password)
                    binding.passwordId.requestFocus()
                }
                else -> {
                    loginViewModel.shouldGoToTheNextPage()
                }
            }
        }
    }
}