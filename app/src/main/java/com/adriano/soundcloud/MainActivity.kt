package com.adriano.soundcloud

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.adriano.usercomponent.Avatar
import com.adriano.usercomponent.FollowingState
import com.adriano.usercomponent.LiveState
import com.adriano.usercomponent.UserComponentViewState
import com.adriano.usercomponent.UserLocation
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private var viewState = defaultState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userProfile.render(viewState)
        userProfile.setFollowingListener(::handleFollowingEvent)
        handleStates()
    }

    private fun handleStates() {
        isLive.setOnCheckedChangeListener { _, isChecked ->
            val liveState = if (isChecked) LiveState.IsLive else LiveState.NotLive
            updateViewState(viewState.copy(isLive = liveState))
        }
        hasLocation.setOnCheckedChangeListener { _, isChecked ->
            val locationState = if (isChecked) UserLocation.Available("Berlin") else UserLocation.NotAvailable
            updateViewState(viewState.copy(location = locationState))
        }
        hasAvatar.setOnCheckedChangeListener { _, isChecked ->
            val avatar = if (isChecked) Avatar.Available(imageUrl) else Avatar.NotAvailable
            updateViewState(viewState.copy(avatar = avatar))
        }
        darkMode.setOnCheckedChangeListener { _, isChecked ->
            val darkMode = if (isChecked) MODE_NIGHT_YES else MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(darkMode)
        }
    }

    private fun handleFollowingEvent() {
        if (viewState.isFollowing == FollowingState.NotFollowing) {
            updateViewState(viewState.copy(isFollowing = FollowingState.Following))
        } else {
            updateViewState(viewState.copy(isFollowing = FollowingState.NotFollowing))
        }
    }

    private fun updateViewState(newState: UserComponentViewState) {
        viewState = newState
        userProfile.render(viewState)
    }

    private fun defaultState(): UserComponentViewState {
        return UserComponentViewState(
            name = "Adriano Celentano",
            followers = "${123567L.format()} followers",
            location = UserLocation.NotAvailable,
            isLive = LiveState.NotLive,
            avatar = Avatar.NotAvailable
        )
    }

    private fun Long.format(): String {
        return NumberFormat.getInstance().format(this)
    }
}

private const val imageUrl = "https://adrianocelentano.github.io/online-cv/assets/images/image.png"