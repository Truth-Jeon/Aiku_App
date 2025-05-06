package com.aiku.presentation.ui.screen.my.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aiku.presentation.state.user.TermViewState
import com.aiku.presentation.ui.component.topbar.DefaultTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeTermDetailScreen(
    term: TermViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTopAppBar(
            title = stringResource(term.title.getTitleRes())
        )
        Text(term.content)
    }
}