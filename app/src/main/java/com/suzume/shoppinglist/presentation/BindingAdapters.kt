package com.suzume.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.suzume.shoppinglist.R

@BindingAdapter("ErrorInputName")
fun bindNameError(textInputLayout: TextInputLayout, isError: Boolean) {
    if (isError) {
        textInputLayout.error = textInputLayout.context.getString(R.string.error_name)
    }else{
        textInputLayout.error = null
    }
}

@BindingAdapter("ErrorInputCount")
fun bindCountError(textInputLayout: TextInputLayout, isError: Boolean) {
    if (isError) {
        textInputLayout.error = textInputLayout.context.getString(R.string.error_count)
    }else{
        textInputLayout.error = null
    }
}