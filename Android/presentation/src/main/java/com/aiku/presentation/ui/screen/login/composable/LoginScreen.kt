package com.aiku.presentation.ui.screen.login.composable

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aiku.core.R
import com.aiku.core.theme.Caption1_Medium
import com.aiku.core.theme.Headline_1G
import com.aiku.core.theme.Subtitle_4G
import com.aiku.domain.usecase.LoginUseCase
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.FullWidthButtonTextSize
import com.aiku.presentation.theme.KakaoBlack
import com.aiku.presentation.theme.KakaoYellow
import com.aiku.presentation.theme.PrimaryColor
import com.aiku.presentation.theme.ScreenHorizontalPadding
import com.aiku.presentation.theme.Typo
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.screen.login.viewmodel.LoginUiState
import com.aiku.presentation.ui.screen.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginUseCase: LoginUseCase,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onComplete: (isSuccessful: Boolean, idToken: String?, email: String?) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)
            .padding(horizontal = ScreenHorizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(164.dp),
            painter = painterResource(id = R.drawable.char_running),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = stringResource(id = R.string.aiku_slogan),
            style = Subtitle_4G,
            color = CobaltBlue
        )

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = stringResource(id = R.string.aiku),
            style = Headline_1G,
            color = CobaltBlue
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            FullWidthButton(
                background = ButtonDefaults.buttonColors(KakaoYellow),
                content = {
                    Text(
                        text = stringResource(id = R.string.kakao_login),
                        color = KakaoBlack,
                        fontSize = FullWidthButtonTextSize
                    )
                },
                onClick = {
                    loginViewModel.login(useKakaoTalk = true, loginUseCase)
                }
            )

            Image(
                modifier = Modifier
                    .padding(start = 27.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.ic_kakao),
                contentDescription = stringResource(id = R.string.kakao_login)
            )

        }

        Text(
            modifier = Modifier
                .padding(top = 14.dp, end = 4.dp)
                .align(Alignment.End)
                .clickable { loginViewModel.login(useKakaoTalk = false, loginUseCase) },
            text = stringResource(id = R.string.kakao_account_login),
            style = Caption1_Medium,
            color = Typo
        )
    }

    LaunchedEffect(Unit) {
        loginViewModel.loginUiState.collect { uiState ->
            when (uiState) {
                LoginUiState.Idle -> {}
                LoginUiState.Loading -> {}
                LoginUiState.Success -> {
                    onComplete(true, null, null)}
                LoginUiState.KakaoOIDCError, LoginUiState.KakaoServerError -> {
                }
                is LoginUiState.ServerError -> {
                    onComplete(false, uiState.idToken, uiState.email)
                }
            }
        }
    }
}
