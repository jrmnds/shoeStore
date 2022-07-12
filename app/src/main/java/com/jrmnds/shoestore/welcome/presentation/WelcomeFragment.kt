package com.jrmnds.shoestore.welcome.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var welcomeBinding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBinders(inflater, container)
        buttonListener()
        return welcomeBinding.root
    }

    private fun buttonListener() {
        welcomeBinding.nextButtonId.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToInstructionsFragment())
        }
    }

    private fun setBinders(inflater: LayoutInflater, container: ViewGroup?) {
        welcomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome,
            container,
            false
        )
        welcomeBinding.lifecycleOwner = this
        (activity as AppCompatActivity).supportActionBar?.title = "Welcome"
    }
}