package com.suzume.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.suzume.shoppinglist.App
import com.suzume.shoppinglist.R
import com.suzume.shoppinglist.databinding.ActivityMainBinding
import com.suzume.shoppinglist.presentation.adapter.ShopListAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var adapter: ShopListAdapter

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        setupOnClickListener()
        setupOnLongClickListener()
        setupOnSwipe()

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun init() {
        binding.rvShoppingList.adapter = adapter
    }

    private fun setupOnLongClickListener() {
        adapter.onLongClickListener = {
            viewModel.changeEnabledShopItem(it)
        }
    }

    private fun setupOnClickListener() {
        adapter.onClickListener = {
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentEditItem(this, it.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }

        binding.floatingActionButton.setOnClickListener {
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentAddItem(this))
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
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

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

}