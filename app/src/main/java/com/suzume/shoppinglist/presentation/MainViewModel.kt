package com.suzume.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.suzume.shoppinglist.data.ShopListRepositoryImpl
import com.suzume.shoppinglist.domain.DeleteShopItemUseCase
import com.suzume.shoppinglist.domain.EditShopItemUseCase
import com.suzume.shoppinglist.domain.GetShopListUseCase
import com.suzume.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList: LiveData<List<ShopItem>> = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnabledShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }

}