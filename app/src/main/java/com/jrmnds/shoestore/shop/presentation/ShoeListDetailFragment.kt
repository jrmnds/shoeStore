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

    private lateinit var binding: FragmentShoeListDetailBinding
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
        return binding.root
    }

    private fun setBinders(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_list_detail,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.shoeDetailsViewModel = shoesListViewModel
        (activity as AppCompatActivity).supportActionBar?.title = "Add a new Shoe"
    }

    private fun observeFields() {
        with(shoesListViewModel) {

            notNullNameShoe.observe(viewLifecycleOwner) { shoeName ->
                when {
                    shoeName.isEmpty() -> {
                        binding.shoeNameLabelId.error =
                            getString(R.string.shoe_name_not_empty)
                        binding.shoeNameLabelId.requestFocus()
                    }
                }
            }

            notNullshoeCompany.observe(viewLifecycleOwner) { shoeCompany ->
                when {
                    shoeCompany.isEmpty() -> {
                        binding.shoesCompanyLabelId.error =
                            getString(R.string.shoes_company_not_empty)
                        binding.shoesCompanyLabelId.requestFocus()
                    }
                }
            }

            notNullshoeSize.observe(viewLifecycleOwner) { shoeSize ->
                when {
                    shoeSize.isEmpty() -> {
                        binding.sizeLabelId.error =
                            getString(R.string.shoe_size_not_empty)
                        binding.sizeLabelId.requestFocus()
                    }
                }
            }

            notNullshoeDescription.observe(viewLifecycleOwner) { shoeDescription ->
                when {
                    shoeDescription.isEmpty() -> {
                        binding.descriptionLabelId.error =
                            getString(R.string.shoe_description_not_empty)
                        binding.descriptionLabelId.requestFocus()
                    }
                }
            }

            notNullShoeImage.observe(viewLifecycleOwner) { shoeImage ->
                when {
                    shoeImage.isEmpty() -> {
                        Toast.makeText(
                            context,
                            getString(R.string.shoe_image_not_null),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        binding.shoesImagePreviewId.visibility = View.VISIBLE
                        GlideHelper.converteFromBase64ToImage(
                            shoeImage, this@ShoeListDetailFragment.context!!, R.drawable.pairshoes,
                            binding.shoesImagePreviewId
                        )
                    }
                }
            }

            changeImageStateLabel.observe(viewLifecycleOwner) {
                binding.shoesImagePreviewId.visibility = View.GONE
            }

            shouldGoToTheNextPage.observe(viewLifecycleOwner) { shouldGoToAnotherPage ->
                when {
                    shouldGoToAnotherPage -> {
                        clearFields()
                        findNavController().navigate(ShoeListDetailFragmentDirections.actionShoeListDetailFragmentToShoesListFragment())
                    }
                }
            }
        }
    }

    private fun clearFields() {
        binding.shoesImagePreviewId.visibility = View.GONE
        binding.shoeNameLabelId.text.clear()
        binding.shoesCompanyLabelId.text.clear()
        binding.descriptionLabelId.text.clear()
        binding.sizeLabelId.text.clear()
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
        binding.addImageButtonId.setOnClickListener {
            loadImage.launch("image/*")
        }
    }


}