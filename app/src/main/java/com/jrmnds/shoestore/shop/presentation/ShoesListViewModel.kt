package com.jrmnds.shoestore.shop.presentation

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jrmnds.shoestore.shop.model.Shoe
import com.jrmnds.shoestore.shop.model.ShoesList
import com.jrmnds.shoestore.utils.GlideHelper.convertImageToBase64

class ShoesListViewModel : ViewModel() {

    private lateinit var shoe: Shoe
    private var shoesListData: ShoesList = ShoesList(mutableListOf())


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
    private var _changeImageStateLabel = MutableLiveData<Boolean>()

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

    val changeImageStateLabel: LiveData<Boolean>
        get() = _changeImageStateLabel


    fun setShoeData() {
        _notNullNameShoe.value = shoeName
        _notNullshoeCompany.value = shoeCompany
        _notNullshoeSize.value = shoeSize
        _notNullshoeDescription.value = shoeDescription
        _notNullShoeImage.value = imageShoe
        validateShoesField()
    }

    private fun addShoeToShoeList() {
        createShoeObject()
        shoesListData.shoesList.add(shoe)
        _shoeList.value = shoesListData
    }

    private fun createShoeObject(){
        shoe = Shoe(notNullNameShoe.value!!, notNullshoeSize.value!!.toDouble(),
        notNullshoeCompany.value!!, notNullshoeDescription.value!!, notNullShoeImage.value!!)
    }

    private fun validateShoesField() {
        when {
            (_notNullNameShoe.value!!.isNotEmpty() && _notNullshoeCompany.value!!.isNotEmpty()
                    && _notNullshoeSize.value!!.isNotEmpty() && _notNullshoeDescription.value!!.isNotEmpty() &&
                    _notNullShoeImage.value!!.isNotEmpty()) -> {

                addShoeToShoeList()
                _shouldGoToTheNextPage.value = true
                resetPageLogic()
                clearImageInput()
            }
            else -> {
                _shouldGoToTheNextPage.value = false
            }
        }
    }


    private fun resetPageLogic() {
        _shouldGoToTheNextPage.value = false
        _changeImageStateLabel.value = true
    }

    private fun clearImageInput(){
        imageShoe = ""
    }

    fun setImage(data: Uri, contentResolver: ContentResolver) {
        imageShoe = convertImageToBase64(data, contentResolver)
        _notNullShoeImage.value = imageShoe
    }
}