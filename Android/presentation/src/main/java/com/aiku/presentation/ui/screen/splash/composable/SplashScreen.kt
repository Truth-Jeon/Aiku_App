package com.aiku.presentation.ui.screen.splash.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aiku.core.R
import com.aiku.core.theme.Headline_1G
import com.aiku.core.theme.Subtitle_4G
import com.aiku.domain.usecase.LoginUseCase
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.ui.screen.login.viewmodel.AutoLoginUiState
import com.aiku.presentation.ui.screen.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    loginUseCase: LoginUseCase,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onComplete: (isSuccessful: Boolean) -> Unit
) {
    val autoLoginUiState by loginViewModel.autoLoginUiState.collectAsStateWithLifecycle()

    // 자동 로그인 시도
    LaunchedEffect(Unit) {
        loginViewModel.autoLogin(loginUseCase)
    }
    LaunchedEffect(autoLoginUiState) {
        delay(2000)
        when (autoLoginUiState) {
            AutoLoginUiState.Idle -> Unit
            AutoLoginUiState.Loading -> Unit
            AutoLoginUiState.Success -> {
                onComplete(true)
            } // TODO : 홈 화면으로 이동
            AutoLoginUiState.ServerError, AutoLoginUiState.TokenNotFound -> {
                onComplete(false)
            } // TODO : 로그인 화면으로 이동
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.aiku_slogan),
            style = Subtitle_4G,
            color = CobaltBlue
        )

        Text(
            text = stringResource(id = R.string.aiku),
            style = Headline_1G,
            color = CobaltBlue
        )


        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.char_group),
            contentDescription = null
        )


    }
}




