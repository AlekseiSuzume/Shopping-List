package com.suzume.shoppinglist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.suzume.shoppinglist.domain.ShopItem

class ShopListDiffCallback : DiffUtil.ItemCallback<ShopItem>() {

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}