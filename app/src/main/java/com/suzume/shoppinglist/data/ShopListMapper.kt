package com.suzume.shoppinglist.data

import com.suzume.shoppinglist.data.model.ShopItemDbModel
import com.suzume.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun mapShopItemToDbModel(shopItem: ShopItem): ShopItemDbModel = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    fun mapDbModelToShopItem(shopItemDbModel: ShopItemDbModel): ShopItem = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enabled = shopItemDbModel.enabled
    )

    fun mapDbModelListToListEntity(list: List<ShopItemDbModel>): List<ShopItem> {
        return list.map { mapDbModelToShopItem(it) }
    }

}