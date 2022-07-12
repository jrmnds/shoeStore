package com.jrmnds.shoestore.login.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jrmnds.shoestore.login.model.LoginModel
import java.util.regex.Pattern

class LoginViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    private val _passWord = MutableLiveData<String>()

    private val _shouldCallNextPage = MutableLiveData<Boolean>()

    private val _isEmailValid = MutableLiveData<Boolean>()
    private val _isPasswordNotEmpty = MutableLiveData<Boolean>()


    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    val isPasswordNotEmpty: LiveData<Boolean>
        get() = _isPasswordNotEmpty

    val shouldCallNextPage: LiveData<Boolean>
        get() = _shouldCallNextPage

    private val _passwordPattern = Pattern.compile(
        ".{8,}"
    )

    fun setData(userLogin: LoginModel) {
        _email.value = userLogin.email
        _passWord.value = userLogin.password
    }

    private fun validateEmail() {
        _isEmailValid.value =
            _email.value!!.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(_email.value!!).matches()
    }

    private fun validatePassword() {
        _isPasswordNotEmpty.value = _passWord.value!!.isNotEmpty() &&
                _passwordPattern.matcher(_passWord.value!!).matches()
    }

    fun shouldGoToTheNextPage(){
        if(isEmailValid.value == true && isPasswordNotEmpty.value == true){
            _shouldCallNextPage.value = true
        }
    }

    fun validateFields() {
        validateEmail()
        validatePassword()
    }



}