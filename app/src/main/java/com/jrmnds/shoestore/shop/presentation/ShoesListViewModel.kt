package com.jrmnds.shoestore.shop.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jrmnds.shoestore.shop.model.Shoe
import com.jrmnds.shoestore.shop.model.ShoesList
import com.jrmnds.shoestore.utils.Base64ExampleImage

class ShoesListViewModel: ViewModel() {

    private lateinit var shoe: Shoe
    private lateinit var shoesListData: ShoesList

    private var _shoeList = MutableLiveData<ShoesList>()

    val shoeList: LiveData<ShoesList>
        get() = _shoeList

    init {
        createShoeObjectsToList()
    }

    private fun createShoeObjectsToList(){
        shoesListData = ShoesList(mutableListOf())
        shoe = Shoe("Nike DX 1", 24.5, "Nike", "Just a shoe for a Test", Base64ExampleImage.base64TestImage)
        shoesListData.shoesList.add(shoe)
        shoe = Shoe("Nike DX 1", 24.5, "Nike", "Just a shoe for a Test", Base64ExampleImage.base64TestImage)
        shoesListData.shoesList.add(shoe)
        shoe = Shoe("Nike DX 1", 24.5, "Nike", "Just a shoe for a Test", Base64ExampleImage.base64TestImage)
        shoesListData.shoesList.add(shoe)
        shoe = Shoe("Nike DX 1", 24.5, "Nike", "Just a shoe for a Test", Base64ExampleImage.base64TestImage)
        shoesListData.shoesList.add(shoe)
        _shoeList.value = shoesListData
    }


}