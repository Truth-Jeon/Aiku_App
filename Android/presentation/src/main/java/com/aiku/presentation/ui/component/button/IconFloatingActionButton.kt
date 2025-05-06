package com.aiku.presentation.ui.component.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aiku.presentation.theme.CobaltBlue

@Composable
fun IconFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    @StringRes contentDescription: Int,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors().copy(
        containerColor = CobaltBlue
    )
) {
    IconButton(
        modifier = modifier.size(48.dp),
        onClick = onClick,
        colors = colors
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = stringResource(id = contentDescription))
    }
}