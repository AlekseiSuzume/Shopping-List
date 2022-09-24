package com.suzume.shoppinglist.di

import android.app.Application
import com.suzume.shoppinglist.data.ShopListRepositoryImpl
import com.suzume.shoppinglist.data.database.AppDatabase
import com.suzume.shoppinglist.data.database.ShopListDao
import com.suzume.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }

    }

}