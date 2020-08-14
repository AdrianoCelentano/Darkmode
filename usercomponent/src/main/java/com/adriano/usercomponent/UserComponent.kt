package com.adriano.usercomponent

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.adriano.usercomponent.extensions.clipCircle
import com.adriano.usercomponent.extensions.getColorId
import com.adriano.usercomponent.extensions.loopVectorAnimation
import com.adriano.usercomponent.extensions.stopVectorAnimation
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip

class UserComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.user_component_layout, this, true)
    }

    private var followingListener: (() -> Unit)? = null
    private val userFollowChip: Chip = findViewById(R.id.userFollowChip)
    private val userNameTextView: TextView = findViewById(R.id.userNameTextView)
    private val userAvatarImageView: ImageView = findViewById(R.id.userAvatarImageView)
    private val userLocationTextView: TextView = findViewById(R.id.userLocationTextView)
    private val userFollowersTextView: TextView = findViewById(R.id.userFollowersTextView)
    private val userLiveIconImageView: ImageView = findViewById(R.id.userLiveIconImageView)
    private val userLiveLinearLayout: LinearLayout = findViewById(R.id.userLiveLinearLayout)

    init {
        initAvatar()
        setBackgroundColor(context.getColorId(R.attr.colorBackground))
        userFollowChip.setOnCheckedChangeListener { _, _ -> followingListener?.invoke() }
    }

    private fun initAvatar() {
        applyAvatarBorderTransparency()
        userAvatarImageView.clipCircle()
    }

    private fun applyAvatarBorderTransparency() {
        findViewById<FrameLayout>(R.id.userAvatarFrameLayout)
            .foreground
            .alpha = AVATAR_BORDER_TRANSPARENCY
    }

    fun setFollowingListener(listener: (() -> Unit)?) {
        followingListener = listener
    }

    fun render(viewState: UserComponentViewState) {
        TransitionManager.beginDelayedTransition(this)
        userNameTextView.text = viewState.name
        userFollowersTextView.text = viewState.followers
        renderLive(viewState.isLive)
        renderAvatar(viewState.avatar)
        renderLocation(viewState.location)
        renderFollowing(viewState.isFollowing)
    }

    private fun renderFollowing(followingState: FollowingState) {
        userFollowChip.setText(followingState.stringId)
        when (followingState) {
            FollowingState.Following -> userFollowChip.isChecked = true
            FollowingState.NotFollowing -> userFollowChip.isChecked = false
        }
    }

    private fun renderAvatar(avatar: Avatar) {
        when (avatar) {
            is Avatar.Available -> loadImage(avatar.imageUrl)
            Avatar.NotAvailable -> userAvatarImageView.setImageResource(R.drawable.avatar_placeholder_light)
        }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.avatar_placeholder_light)
            .into(userAvatarImageView);
    }

    private fun renderLive(liveState: LiveState) {
        when (liveState) {
            LiveState.IsLive -> showLive()
            LiveState.NotLive -> hideLive()
        }
    }

    private fun showLive() {
        userLiveLinearLayout.visibility = VISIBLE
        userLiveIconImageView.loopVectorAnimation(R.drawable.avd_soundwave_12_soundcloudorange)
    }

    private fun hideLive() {
        userLiveLinearLayout.visibility = GONE
        userLiveIconImageView.stopVectorAnimation()
    }

    private fun renderLocation(location: UserLocation) {
        when (location) {
            is UserLocation.Available -> showLocation(location)
            UserLocation.NotAvailable -> userLocationTextView.visibility = GONE
        }
    }

    private fun showLocation(location: UserLocation.Available) {
        userLocationTextView.visibility = VISIBLE
        userLocationTextView.text = location.value
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        followingListener = null
        userLiveIconImageView.stopVectorAnimation()
    }

    private companion object {
        const val AVATAR_BORDER_TRANSPARENCY = 19
    }
}