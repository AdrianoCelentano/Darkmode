package com.adriano.usercomponent.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Context.getColorId(@AttrRes reference: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(reference, typedValue, true)
    return typedValue.data
}