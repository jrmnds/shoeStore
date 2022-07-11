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

    var imageShoe: String = ""
    var shoeName: String = ""
    var shoeCompany: String = ""
    var shoeDescription: String = ""
    var shoeSize: String = ""

    private var _shoeList = MutableLiveData<ShoesList>()
    private var _notNullNameShoe = MutableLiveData<String>()
    private var _notNullshoeCompany = MutableLiveData<String>()
    private var _notNullshoeDescription = MutableLiveData<String>()
    private var _notNullshoeSize = MutableLiveData<String>()
    private var _notNullShoeImage = MutableLiveData<String>()

    private val _shouldGoToTheNextPage = MutableLiveData<Boolean>()


    val shoeList: LiveData<ShoesList>
        get() = _shoeList

    val notNullNameShoe: LiveData<String>
        get() = _notNullNameShoe

    val notNullshoeCompany: LiveData<String>
        get() = _notNullshoeCompany

    val notNullshoeDescription: LiveData<String>
        get() = _notNullshoeDescription

    val notNullshoeSize: LiveData<String>
        get() = _notNullshoeSize

    val notNullShoeImage: LiveData<String>
        get() = _notNullShoeImage

    val shouldGoToTheNextPage: LiveData<Boolean>
        get() = _shouldGoToTheNextPage


    init {
        createShoeObjectsToList()
        imageShoe = Base64ExampleImage.base64TestImage
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

    fun setShoeData(){
        _notNullNameShoe.value = shoeName
        _notNullshoeCompany.value = shoeCompany
        _notNullshoeSize.value = shoeSize
        _notNullshoeDescription.value = shoeDescription
        _notNullShoeImage.value = imageShoe
        addShoeToShoeList(createShoeObject())
        validateShoesField()
    }

    private fun addShoeToShoeList(shoe: Shoe) {
        shoesListData.shoesList.add(shoe)
        _shoeList.value = shoesListData
    }

    private fun createShoeObject() : Shoe {
      return shoe.apply {
            name = notNullNameShoe.value.toString()
            company = notNullshoeCompany.value.toString()
            description = notNullshoeDescription.value.toString()
            size = notNullshoeSize.value!!.toDouble()
            image = imageShoe
        }
    }

    private fun validateShoesField(){
        _shouldGoToTheNextPage.value = (_notNullNameShoe.value!!.isNotEmpty() && _notNullshoeCompany.value!!.isNotEmpty()
                && _notNullshoeSize.value!!.isNotEmpty() && _notNullshoeDescription.value!!.isNotEmpty() &&
                _notNullShoeImage.value!!.isNotEmpty())
    }



}