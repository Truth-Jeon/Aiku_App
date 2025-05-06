package com.aiku.presentation.ui.component.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Caption1
import com.aiku.presentation.navigation.route.BtmNav
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.Typo

@Composable
fun BottomNavigation(
    bottomItems: List<BtmNav>,
    selectedBottomItem: BtmNav,
    onTabSelected: (BtmNav) -> Unit
) {

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Gray02), shape = RectangleShape)
            .padding(top = 1.dp),
        containerColor = Color.White
    ) {
        bottomItems.forEach {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = stringResource(id = it.labelId)
                    )
                },
                label = { Text(stringResource(id = it.labelId), style = Caption1) },
                selected = selectedBottomItem == it,
                onClick = {
                    onTabSelected(it)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green5,
                    unselectedIconColor = Gray03,
                    selectedTextColor = Typo,
                    unselectedTextColor = Gray03,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
