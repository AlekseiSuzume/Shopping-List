package com.suzume.shoppinglist.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.suzume.shoppinglist.databinding.ItemShopEnabledBinding
import com.suzume.shoppinglist.domain.ShopItem

class ItemShopEnabledViewHolder(private val binding: ItemShopEnabledBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(shopItem: ShopItem) {
        binding.shopItem = shopItem
    }

}