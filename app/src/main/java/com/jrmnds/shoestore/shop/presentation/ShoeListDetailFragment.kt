package com.jrmnds.shoestore.shop.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentShoeListDetailBinding


class ShoeListDetailFragment : Fragment() {

    private lateinit var bindingShoeListDetail: FragmentShoeListDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBinders(inflater, container)
        return bindingShoeListDetail.root
    }

    private fun setBinders(inflater: LayoutInflater, container: ViewGroup?) {
        bindingShoeListDetail = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_list_detail,
            container,
            false
        )

        bindingShoeListDetail.lifecycleOwner = this
        (activity as AppCompatActivity).supportActionBar?.title = "Add a new Shoe"
    }
}