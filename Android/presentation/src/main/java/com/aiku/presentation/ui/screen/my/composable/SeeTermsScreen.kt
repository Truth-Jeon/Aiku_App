package com.aiku.presentation.ui.screen.my.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aiku.core.theme.Body2
import com.aiku.domain.model.user.type.TermType
import com.aiku.core.R
import com.aiku.presentation.state.user.TermViewState
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.ui.component.topbar.DefaultTopAppBar
import com.aiku.presentation.ui.screen.my.viewmodel.SeeTermsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeTermsScreen(
    modifier: Modifier = Modifier,
    onTermItemClicked: (TermViewState) -> Unit = {},
    viewModel: SeeTermsViewModel = hiltViewModel()
) {

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        DefaultTopAppBar(
            title = stringResource(R.string.item_see_terms)
        )
        TermType.entries.forEach {
            TermItem(
                modifier = Modifier.clickable {
                    viewModel.onTermItemClicked(it, onTermItemClicked)
                }.padding(20.dp),
                title = stringResource(it.getTitleRes())
            )
            if (it != TermType.entries.last()) {
                HorizontalDivider(
                    color = Gray02
                )
            }
        }
    }
}

@Composable
private fun TermItem(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Body2
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = title
        )
    }
}

fun TermType.getTitleRes(): Int {
    return when (this) {
        TermType.SERVICE -> R.string.terms_item_service
        TermType.PERSONALINFO -> R.string.terms_item_privacy
        TermType.MARKETING -> R.string.terms_item_marketing
        TermType.LOCATION -> R.string.terms_item_location
    }
}