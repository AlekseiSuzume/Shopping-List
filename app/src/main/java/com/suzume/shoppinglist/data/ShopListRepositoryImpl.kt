package com.suzume.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.suzume.shoppinglist.data.database.AppDatabase
import com.suzume.shoppinglist.domain.ShopItem
import com.suzume.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    private val dao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapShopItemToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        dao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapShopItemToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        return mapper.mapDbModelToShopItem(dao.getShopItem(shopItemId))
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return MediatorLiveData<List<ShopItem>>().apply {
            addSource(dao.getShopList()) {
                value = mapper.mapDbModelListToListEntity(it)
            }
        }
    }

    //Второй вариант
//    override fun getShopList(): LiveData<List<ShopItem>> {
//        return Transformations.map(dao.getShopList()){
//            mapper.mapDbModelListToListEntity(it)
//        }
//    }

}