package com.aiku.presentation.ui.group.schedule.waiting.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aiku.core.R
import com.aiku.core.theme.Body1
import com.aiku.core.theme.Subtitle_2G
import com.aiku.core.theme.Subtitle_3G
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.group.schedule.waiting.viewmodel.BettingUiState
import com.aiku.presentation.ui.group.schedule.waiting.viewmodel.BettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BettingScreen(
    modifier: Modifier = Modifier,
    onBettingComplete: () -> Unit,
    viewModel: BettingViewModel = hiltViewModel()
) {

    val akuInput by viewModel.akuInput.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize().imePadding(),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = viewModel.group.name, style = Subtitle_3G)
            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()).weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(horizontal = 66.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = CobaltBlue
                        )
                    ) {
                        append(viewModel.member.nickname)
                    }
                    append("님을 ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append("꼴찌로 선택하기 위한 베팅금을 설정해 주세요!")
                    }
                },
                style = Subtitle_2G,
                textAlign = TextAlign.Center
            )

            Image(
                modifier = Modifier
                    .padding(top = 140.dp)
                    .padding(horizontal = 66.dp)
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.char_running_boy),
                contentDescription = null,
            )

            BasicTextField(
                value = akuInput,
                onValueChange = viewModel::onBetAkuChanged,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(horizontal = 80.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(50)
                    )
                    .border(1.dp, CobaltBlue, RoundedCornerShape(50)),
                cursorBrush = SolidColor(Color.Transparent),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ), textStyle = TextStyle.Default.copy(
                    color = CobaltBlue,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            ) { innerTextField ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 24.dp),
                ) {
                    if (akuInput.isEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "베팅금을 입력해주세요",
                            style = Body1,
                            fontWeight = FontWeight.SemiBold,
                            color = CobaltBlue
                        )
                    }
                    if (akuInput.isNotEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "$akuInput 아쿠",
                            style = Body1,
                            fontWeight = FontWeight.SemiBold,
                            color = CobaltBlue,
                            maxLines = 1
                        )
                    }
                }
            }
        }
        FullWidthButton(
            modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 36.dp, top = 10.dp),
            enabled = akuInput.isNotEmpty(),
            background = ButtonDefaults.buttonColors().copy(
                containerColor = Green5,
                disabledContainerColor = Gray02,
            ),
            onClick = {
                viewModel.bet()
            }
        ) {
            Text(
                text = "꼴찌로 선택하기",
                style = Subtitle_3G,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.bettingUiState.collect { state ->
            when (state) {
                is BettingUiState.Loading -> {

                }

                is BettingUiState.Success -> {
                    onBettingComplete()
                }

                is BettingUiState.BetAmountNotPositive -> {

                }
            }
        }
    }
}