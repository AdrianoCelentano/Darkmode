package com.adriano.usercomponent.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

internal fun ImageView.loopVectorAnimation(@DrawableRes animatedVectorDrawableId: Int) {
    val animated = AnimatedVectorDrawableCompat.create(context, animatedVectorDrawableId)
    animated?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
            this@loopVectorAnimation.post { animated.start() }
        }
    })
    this.setImageDrawable(animated)
    animated?.start()
}

internal fun ImageView.stopVectorAnimation() {
    val animated = drawable as? AnimatedVectorDrawableCompat
    animated?.clearAnimationCallbacks()
}