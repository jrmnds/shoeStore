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
import com.jrmnds.shoestore.utils.GlideHelper

class ShoesListFragment : Fragment() {

    private lateinit var binding: FragmentShoesListBinding
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
        return binding.root
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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoes_list,
            container,
            false
        )
        (activity as AppCompatActivity).supportActionBar?.title = "Shoes List"
        binding.lifecycleOwner = this
    }

    private fun setObservers() {
        shoesListViewModel.shoeList.observe(viewLifecycleOwner) { setListOnView ->
            setListOnView.shoesList.forEach { shoe ->
                shoeCardListItem = ShoeListCardBinding.inflate(layoutInflater)
                setShoeToCardList(shoe)
            }
        }

        binding.floatingActionButtonId.setOnClickListener {
            findNavController().navigate(ShoesListFragmentDirections.actionShoesListFragmentToShoeListDetailFragment())
        }
    }

    private fun setShoeToCardList(shoeData: Shoe) {
        shoeCardListItem.apply {
            GlideHelper.converteFromBase64ToImage(
                shoeData.image, this@ShoesListFragment.context!!,
                R.drawable.pairshoes, shoeImageId
            )
            sizeIdInformation.text = shoeData.size.toString()
            nameIdInformation.text = shoeData.name
            companyIdInformation.text = shoeData.company
            descriptionIdInformation.text = shoeData.description
        }
        binding.shoesLinearViewId.addView(shoeCardListItem.root)
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