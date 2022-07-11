package com.jrmnds.shoestore.shop.presentation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentShoesListBinding
import com.jrmnds.shoestore.databinding.ShoeListCardBinding
import com.jrmnds.shoestore.shop.model.Shoe
import com.jrmnds.shoestore.utils.Base64ExampleImage
import com.jrmnds.shoestore.utils.GlideHelper

class ShoesListFragment : Fragment() {

    private lateinit var shoesListBinding: FragmentShoesListBinding
    private val shoesListViewModel: ShoesListViewModel by activityViewModels()
    private lateinit var shoeCardListItem: ShoeListCardBinding
    private lateinit var configureIntent: Intent
    private lateinit var callBackPop: OnBackPressedCallback


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBinders(inflater, container)
        setObservers()
        configureNotPopCallBack()
        setHasOptionsMenu(true)
        return shoesListBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout_button -> findNavController().navigate(ShoesListFragmentDirections.actionShoesListFragmentToLoginFragment32())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBinders(inflater: LayoutInflater, container: ViewGroup?) {
        shoesListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoes_list,
            container,
            false
        )
        (activity as AppCompatActivity).supportActionBar?.title = "Shoes List"
        shoesListBinding.lifecycleOwner = this
    }

    private fun setObservers() {
        shoesListViewModel.shoeList.observe(viewLifecycleOwner) { setListOnView ->
            setListOnView.shoesList.forEach { shoe ->
                shoeCardListItem = ShoeListCardBinding.inflate(layoutInflater)
                setShoeToCardList(shoe)
            }
        }
    }

    private fun setShoeToCardList(shoeData: Shoe) {
        shoeCardListItem.apply {
            GlideHelper.converteFromBase64ToImage(
                Base64ExampleImage.base64TestImage, this@ShoesListFragment.context!!,
                R.drawable.pairshoes, shoeImageId
            )
            sizeIdInformation.text = shoeData.size.toString()
            nameIdInformation.text = shoeData.name
            companyIdInformation.text = shoeData.company
            descriptionIdInformation.text = shoeData.description
        }
        shoesListBinding.shoesLinearViewId.addView(shoeCardListItem.root)
    }

    private fun configureNotPopCallBack() {
        callBackPop = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            configureIntent = Intent(Intent.ACTION_MAIN)
            configureIntent.addCategory(Intent.CATEGORY_HOME)
            configureIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(configureIntent)
        }
        callBackPop.isEnabled
    }

}