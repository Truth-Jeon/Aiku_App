package com.aiku.presentation.ui.screen.signup.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aiku.core.R
import com.aiku.core.theme.Caption1
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.presentation.theme.ScreenBottomPadding
import com.aiku.presentation.theme.ScreenHorizontalPadding
import com.aiku.presentation.ui.screen.signup.viewmodel.TermsViewModel
import com.aiku.presentation.ui.screen.signup.viewmodel.LoadTermsUiState

@Composable
fun TermsContentScreen(
    identifier: Int?,
    termsViewModel: TermsViewModel = hiltViewModel()
) {
    val termsUiState by termsViewModel.termsUiState.collectAsState()

    val (termsResource, termsTitle) = when (identifier) {
        0 -> R.raw.terms_of_service1 to R.string.terms_agree_item1
        1 -> R.raw.terms_of_service1 to R.string.terms_agree_item2
        2 -> R.raw.terms_of_service1 to R.string.terms_agree_item3
        3 -> R.raw.terms_of_service1 to R.string.terms_agree_item4
        else -> null to 0
    }

    LaunchedEffect(termsResource) {
        termsResource?.let { termsViewModel.loadTerms(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = ScreenTopPadding, bottom = ScreenBottomPadding)
            .padding(horizontal = ScreenHorizontalPadding)
    ) {
        if (termsTitle != 0) {
            Text(
                text = stringResource(id = termsTitle),
                style = Subtitle3_SemiBold,
                color = Color.Black
            )
        }

        when (val state = termsUiState) {
            is LoadTermsUiState.Loading -> {}
            is LoadTermsUiState.Success -> {
                Text(
                    text = state.content,
                    style = Caption1,
                    color = Color.Black
                )
            }

            is LoadTermsUiState.Failure -> {
                Text(text = state.message)
            }

            else -> {}
        }
    }
}

private val ScreenTopPadding = 40.dp
