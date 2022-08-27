package com.suzume.shoppinglist.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.suzume.shoppinglist.databinding.ItemShopDisabledBinding
import com.suzume.shoppinglist.domain.ShopItem

class ItemShopDisabledViewHolder(private val binding: ItemShopDisabledBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(shopItem: ShopItem) {
        binding.shopItem = shopItem
    }

}