package com.jrmnds.shoestore.login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
        loginBinding.loginViewModel = loginViewModel
        loginBinding.lifecycleOwner = this
    }

    private fun configureViewModel() {
        loginViewModelFactory = LoginViewModelFactory()
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }


    private fun observeFields() {
        loginBinding.loginButton.setOnClickListener {
            loginViewModel.setData(
                LoginModel(
                    loginBinding.emailId.text.toString(),
                    loginBinding.passwordId.text.toString()
                )
            )
            loginViewModel.validateFields()
        }
    }

    private fun observeValidations(){
        loginViewModel.isEmailValid.observe(viewLifecycleOwner) { isValid ->
            if (!isValid) {
                Toast.makeText(activity, R.string.invalid_email, Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.isPasswordNotEmpty.observe(viewLifecycleOwner) { isPasswordValid ->
            if (!isPasswordValid) {
                Toast.makeText(activity, R.string.invalid_password, Toast.LENGTH_SHORT).show()
            }
        }
    }
}