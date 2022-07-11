package com.jrmnds.shoestore.shop.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentShoeListDetailBinding


class ShoeListDetailFragment : Fragment() {

    private lateinit var bindingShoeListDetail: FragmentShoeListDetailBinding
    private val shoesListViewModel: ShoesListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBinders(inflater, container)
        observeFields()
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
        bindingShoeListDetail.shoeDetailsViewModel = shoesListViewModel
        (activity as AppCompatActivity).supportActionBar?.title = "Add a new Shoe"
    }

   private fun observeFields(){
        shoesListViewModel.notNullNameShoe.observe(viewLifecycleOwner){ shoeName ->
            when {
                shoeName.isEmpty() -> {
                    bindingShoeListDetail.shoeNameLabelId.error = getString(R.string.shoe_name_not_empty)
                    bindingShoeListDetail.shoeNameLabelId.requestFocus()
                }
            }
        }

       shoesListViewModel.notNullshoeCompany.observe(viewLifecycleOwner){ shoeCompany ->
           when{
               shoeCompany.isEmpty() -> {
                   bindingShoeListDetail.shoesCompanyLabelId.error = getString(R.string.shoes_company_not_empty)
                   bindingShoeListDetail.shoesCompanyLabelId.requestFocus()
               }
           }
       }

       shoesListViewModel.notNullshoeSize.observe(viewLifecycleOwner){ shoeSize ->
           when{
               shoeSize.isEmpty() -> {
                   bindingShoeListDetail.sizeLabelId.error = getString(R.string.shoe_size_not_empty)
                   bindingShoeListDetail.sizeLabelId.requestFocus()
               }
           }
       }

       shoesListViewModel.notNullshoeDescription.observe(viewLifecycleOwner){ shoeDescription ->
           when{
               shoeDescription.isEmpty() ->{
                   bindingShoeListDetail.descriptionLabelId.error = getString(R.string.shoe_description_not_empty)
                   bindingShoeListDetail.descriptionLabelId.requestFocus()
               }
           }
       }

    }
}