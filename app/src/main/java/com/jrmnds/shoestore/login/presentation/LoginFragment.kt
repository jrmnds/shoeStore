package com.jrmnds.shoestore.login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentLoginBinding

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

}