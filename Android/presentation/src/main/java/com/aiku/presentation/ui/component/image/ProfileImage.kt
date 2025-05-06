package com.aiku.presentation.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.aiku.core.R
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.presentation.state.user.ProfileState

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    profile: ProfileState
) {
    when (profile.type) {
        ProfileType.IMG -> {
            AsyncImage(
                modifier = modifier,
                model = profile.image,
                contentDescription = stringResource(id = R.string.profile_image),
                placeholder = null,
                contentScale = ContentScale.Crop
            )
        }

        ProfileType.CHAR -> {
            DefaultProfileIcon(
                modifier = modifier,
                character = profile.character,
            )
        }
    }
}