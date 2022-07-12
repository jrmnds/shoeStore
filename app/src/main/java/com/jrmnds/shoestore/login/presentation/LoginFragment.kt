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

    private lateinit var loginBinding: FragmentLoginBinding
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
        return loginBinding.root
    }

    private fun setBinders(inflater: LayoutInflater, container: ViewGroup?) {
        loginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        loginBinding.lifecycleOwner = this
        (activity as AppCompatActivity).supportActionBar?.title = "Login"
    }

    private fun configureViewModel() {
        loginViewModelFactory = LoginViewModelFactory()
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }


    private fun observeFields() {
        loginBinding.loginButton.setOnClickListener {
            setLoginData()
            loginViewModel.validateFields()
        }

        loginBinding.createAccountButtonId.setOnClickListener {
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
                loginBinding.emailId.text.toString(),
                loginBinding.passwordId.text.toString()
            )
        )
    }

    private fun observeValidations(){
        loginViewModel.isEmailValid.observe(viewLifecycleOwner) { isValid ->
            when {
                !isValid -> {
                    loginBinding.emailId.error = getString(R.string.invalid_email)
                    loginBinding.emailId.requestFocus()
                }
                else -> {
                    loginViewModel.shouldGoToTheNextPage()
                }
            }
        }

        loginViewModel.isPasswordNotEmpty.observe(viewLifecycleOwner) { isPasswordValid ->
            when {
                !isPasswordValid -> {
                    loginBinding.passwordId.error = getString(R.string.invalid_password)
                    loginBinding.passwordId.requestFocus()
                }
                else -> {
                    loginViewModel.shouldGoToTheNextPage()
                }
            }
        }
    }
}