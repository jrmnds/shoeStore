package com.jrmnds.shoestore.shop.presentation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jrmnds.shoestore.R
import com.jrmnds.shoestore.databinding.FragmentShoeListDetailBinding
import com.jrmnds.shoestore.utils.GlideHelper


class ShoeListDetailFragment : Fragment() {

    private lateinit var bindingShoeListDetail: FragmentShoeListDetailBinding
    private val shoesListViewModel: ShoesListViewModel by activityViewModels()

    private val loadImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { data ->
        shoesListViewModel.setImage(data, requireActivity().contentResolver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBinders(inflater, container)
        getAccess()
        getImage()
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

    private fun observeFields() {
        shoesListViewModel.notNullNameShoe.observe(viewLifecycleOwner) { shoeName ->
            when {
                shoeName.isEmpty() -> {
                    bindingShoeListDetail.shoeNameLabelId.error =
                        getString(R.string.shoe_name_not_empty)
                    bindingShoeListDetail.shoeNameLabelId.requestFocus()
                }
            }
        }

        shoesListViewModel.notNullshoeCompany.observe(viewLifecycleOwner) { shoeCompany ->
            when {
                shoeCompany.isEmpty() -> {
                    bindingShoeListDetail.shoesCompanyLabelId.error =
                        getString(R.string.shoes_company_not_empty)
                    bindingShoeListDetail.shoesCompanyLabelId.requestFocus()
                }
            }
        }

        shoesListViewModel.notNullshoeSize.observe(viewLifecycleOwner) { shoeSize ->
            when {
                shoeSize.isEmpty() -> {
                    bindingShoeListDetail.sizeLabelId.error =
                        getString(R.string.shoe_size_not_empty)
                    bindingShoeListDetail.sizeLabelId.requestFocus()
                }
            }
        }

        shoesListViewModel.notNullshoeDescription.observe(viewLifecycleOwner) { shoeDescription ->
            when {
                shoeDescription.isEmpty() -> {
                    bindingShoeListDetail.descriptionLabelId.error =
                        getString(R.string.shoe_description_not_empty)
                    bindingShoeListDetail.descriptionLabelId.requestFocus()
                }
            }
        }

        shoesListViewModel.notNullShoeImage.observe(viewLifecycleOwner){ shoeImage ->
            when{
                shoeImage.isEmpty() -> {
                    Toast.makeText(context, getString(R.string.shoe_image_not_null), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    bindingShoeListDetail.shoesImagePreviewId.visibility = View.VISIBLE
                    GlideHelper.converteFromBase64ToImage(shoeImage, this@ShoeListDetailFragment.context!!, R.drawable.pairshoes,
                        bindingShoeListDetail.shoesImagePreviewId)
                }
            }
        }

        shoesListViewModel.changeImageStateLabel.observe(viewLifecycleOwner){
            bindingShoeListDetail.shoesImagePreviewId.visibility = View.GONE
        }

        shoesListViewModel.shouldGoToTheNextPage.observe(viewLifecycleOwner) { shouldGoToAnotherPage ->
            when {
                shouldGoToAnotherPage -> {
                    clearFields()
                    findNavController().navigate(ShoeListDetailFragmentDirections.actionShoeListDetailFragmentToShoesListFragment())
                }
            }
        }
    }

    private fun clearFields() {
        bindingShoeListDetail.shoesImagePreviewId.visibility = View.GONE
        bindingShoeListDetail.shoeNameLabelId.text.clear()
        bindingShoeListDetail.shoesCompanyLabelId.text.clear()
        bindingShoeListDetail.descriptionLabelId.text.clear()
        bindingShoeListDetail.sizeLabelId.text.clear()
    }

    private fun getAccess() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Log.i("Granted", "Access Granted")
            } else {
                Log.e("Denied", "Access Denied")
            }
        }
        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun getImage() {
        bindingShoeListDetail.addImageButtonId.setOnClickListener {
            loadImage.launch("image/*")
        }
    }


}