package com.aiku.presentation.ui.screen.my.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.presentation.theme.Gray02

enum class MyPageListType(
    @StringRes val titleRes: Int
) {
    NOTIFICATION(R.string.item_notification_setting),
    ACCOUNT(R.string.item_account),
    SEE_TERMS(R.string.item_see_terms),
    SET_PERMISSIONS(R.string.item_set_permissions),
    INQUIRY(R.string.item_inquiry),
    HELP(R.string.item_help),
    GIVE_RATING(R.string.item_give_rating),
}

@Composable
fun MyPageList(
    modifier: Modifier = Modifier,
    onItemClicked: (MyPageListType) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        MyPageListType.entries.forEach {
            MyPageListItem(
                type = it,
                modifier = Modifier,
                onClick = onItemClicked
            )
            if (it != MyPageListType.entries.last()) {
                HorizontalDivider(
                    color = Gray02
                )
            }
        }
    }
}

@Composable
private fun MyPageListItem(
    type: MyPageListType,
    modifier: Modifier = Modifier,
    onClick: (MyPageListType) -> Unit = {}
) {

    Row(
        modifier = modifier.background(Color.White).clickable {
             onClick(type)
        }.padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(type.titleRes), style = Body2)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = stringResource(type.titleRes),
        )
    }
}

@Composable
@Preview
private fun MyPageListPreview() {
    MyPageList()
}