package com.suzume.shoppinglist

import android.app.Application
import com.suzume.shoppinglist.di.DaggerApplicationComponent

class App : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}