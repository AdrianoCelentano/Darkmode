package com.adriano.usercomponent.extensions

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

internal fun View.clipCircle() {
    outlineProvider = circleOutlineProvider()
    clipToOutline = true
}

private fun circleOutlineProvider(): ViewOutlineProvider {
    return object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(
                view.paddingLeft,
                view.paddingTop,
                view.width - view.paddingRight,
                view.height - view.paddingBottom
            )
        }
    }
}