package com.suzume.shoppinglist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suzume.shoppinglist.databinding.ItemShopDisabledBinding
import com.suzume.shoppinglist.databinding.ItemShopEnabledBinding
import com.suzume.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListAdapter @Inject constructor () : ListAdapter<ShopItem, RecyclerView.ViewHolder>(ShopListDiffCallback()) {

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
    }

    var onLongClickListener: ((ShopItem) -> Unit)? = null
    var onClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ENABLED -> {
                val binding = ItemShopEnabledBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                ItemShopEnabledViewHolder(binding)
            }
            VIEW_TYPE_DISABLED -> {
                val binding = ItemShopDisabledBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                ItemShopDisabledViewHolder(binding)
            }
            else -> {
                throw RuntimeException("ViewType not found $viewType")
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shopItem = getItem(position)
        when (holder) {
            is ItemShopEnabledViewHolder -> holder.bind(shopItem)
            is ItemShopDisabledViewHolder -> holder.bind(shopItem)
        }
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].enabled) {
            true -> VIEW_TYPE_ENABLED
            else -> VIEW_TYPE_DISABLED
        }
    }
}