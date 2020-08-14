package com.adriano.usercomponent

import androidx.annotation.StringRes

data class UserComponentViewState(
    val name: String,
    val followers: String,
    val avatar: Avatar = Avatar.NotAvailable,
    val isLive: LiveState = LiveState.NotLive,
    val location: UserLocation = UserLocation.NotAvailable,
    val isFollowing: FollowingState = FollowingState.NotFollowing
)

sealed class FollowingState(@StringRes val stringId: Int) {
    object Following : FollowingState(R.string.user_following)
    object NotFollowing : FollowingState(R.string.user_not_following)
}

sealed class LiveState {
    object IsLive : LiveState()
    object NotLive : LiveState()
}

sealed class UserLocation {
    object NotAvailable : UserLocation()
    data class Available(val value: String) : UserLocation()
}

sealed class Avatar {
    object NotAvailable : Avatar()
    data class Available(val imageUrl: String) : Avatar()
}
