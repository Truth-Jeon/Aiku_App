package com.aiku.presentation.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aiku.presentation.state.user.ProfileState
import com.aiku.presentation.ui.component.background.CircularBackground
import com.aiku.presentation.util.getColor

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    showClickRipple: Boolean = true,
    onClick: () -> Unit = {},
    profile: ProfileState
) {
    CircularBackground(
        modifier = modifier,
        color = profile.background.getColor(),
        showClickRipple = showClickRipple,
        onClick = onClick,
    ) {
        ProfileImage(
            profile = profile,
            modifier = Modifier
        )
    }
}
