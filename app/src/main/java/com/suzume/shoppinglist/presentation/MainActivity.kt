package com.suzume.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.suzume.shoppinglist.databinding.ActivityMainBinding
import com.suzume.shoppinglist.presentation.adapter.ShopListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        init()
        setupOnClickListener()
        setupOnLongClickListener()
        setupOnSwipe()

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = ShopListAdapter()
        binding.rvShoppingList.adapter = adapter
    }

    private fun setupOnLongClickListener() {
        adapter.onLongClickListener = {
            viewModel.changeEnabledShopItem(it)
        }
    }

    private fun setupOnClickListener() {
        adapter.onClickListener = {
            startActivity(ShopItemActivity.newIntentEditItem(this, it.id))
        }

        binding.floatingActionButton.setOnClickListener {
            startActivity(ShopItemActivity.newIntentAddItem(this))
        }
    }

    private fun setupOnSwipe() {
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItemId = viewHolder.adapterPosition
                val shopItem = adapter.currentList[shopItemId]
                if (direction == ItemTouchHelper.LEFT) {
                    viewModel.deleteShopItem(shopItem)
                }
            }
        }
        itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvShoppingList)
    }

}