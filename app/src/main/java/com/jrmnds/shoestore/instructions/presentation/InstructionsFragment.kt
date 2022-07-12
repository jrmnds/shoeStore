package com.jrmnds.shoestore.instructions.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentInstructionsBinding

class InstructionsFragment : Fragment() {

    private lateinit var instructionsBinding: FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        setBinders(inflater, container)
        buttonListener()
        return instructionsBinding.root
    }

    private fun buttonListener() {
        instructionsBinding.buttonNextId.setOnClickListener {
            findNavController().navigate(InstructionsFragmentDirections.actionInstructionsFragmentToShoesListFragment())
        }
    }

    private fun setBinders(inflater: LayoutInflater, container: ViewGroup?) {
        instructionsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_instructions,
            container,
            false
        )
        instructionsBinding.lifecycleOwner = this
        (activity as AppCompatActivity).supportActionBar?.title = "Instructions"
    }
}