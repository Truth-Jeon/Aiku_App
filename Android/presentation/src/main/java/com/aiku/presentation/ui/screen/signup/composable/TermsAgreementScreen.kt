package com.aiku.presentation.ui.screen.signup.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aiku.core.R
import com.aiku.core.theme.Headline_2G
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.ScreenBottomPadding
import com.aiku.presentation.theme.ScreenHorizontalPadding
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.component.checkbox.CheckBoxWithText
import com.aiku.presentation.ui.component.checkbox.CheckMarkWithText
import com.aiku.presentation.ui.screen.signup.viewmodel.TermsViewModel

@Composable
fun TermsAgreementScreen(
    idToken: String,
    email: String?,
    onNavigateToProfileEditScreen: (idToken : String, email : String?) -> Unit,
    onNavigateToTermContentScreen: (identifier: Int) -> Unit,
    termsViewModel: TermsViewModel = hiltViewModel(),
) {

    val checkedStates by termsViewModel.checkedStates.collectAsState()
    val checkedAll by termsViewModel.checkedAll.collectAsState()
    val btnEnabled by termsViewModel.btnEnabled.collectAsState()

    val items = listOf(
        stringResource(id = R.string.terms_agree_item1) + stringResource(id = R.string.terms_agree_required),
        stringResource(id = R.string.terms_agree_item2) + stringResource(id = R.string.terms_agree_required),
        stringResource(id = R.string.terms_agree_item3) + stringResource(id = R.string.terms_agree_required),
        stringResource(id = R.string.terms_agree_item4) + stringResource(id = R.string.terms_agree_optional)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = ScreenTopPadding, bottom = ScreenBottomPadding)
            .padding(horizontal = ScreenHorizontalPadding)
    ) {

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(TermsCharSize),
                    painter = painterResource(id = R.drawable.char_head_boy),
                    contentDescription = stringResource(id = R.string.character_man)
                )
                Image(
                    modifier = Modifier.size(18.dp, 14.dp),
                    painter = painterResource(id = R.drawable.ic_hi),
                    contentDescription = stringResource(id = R.string.text_hi)
                )
            }

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.terms_welcome_aiku),
                style = Headline_2G
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // "전체 동의" 체크박스
        CheckBoxWithText(
            modifier = Modifier.padding(bottom = TermsItemSpacing),
            checkedState = checkedAll,
            onCheckedChange = { isChecked ->
                termsViewModel.onCheckedChanged(-1, isChecked)
            },
            content = stringResource(id = R.string.terms_agree_all)
        )

        // 각각의 항목 체크박스
        items.forEachIndexed { index, item ->
            CheckMarkWithText(
                modifier = Modifier.padding(top = TermsItemSpacing),
                checkedState = checkedStates[index],
                onCheckedChange = { isChecked ->
                    termsViewModel.onCheckedChanged(index, isChecked)
                },
                content = item,
                onNavigateToTermContent = { onNavigateToTermContentScreen(index) }
            )
        }

        FullWidthButton(
            modifier = Modifier.padding(top = ButtonContentPadding),
            enabled = btnEnabled,
            background = ButtonDefaults.buttonColors(
                containerColor = Green5,
                disabledContainerColor = Gray02
            ),
            onClick = { onNavigateToProfileEditScreen(idToken, email) },
            content = {
                Text(
                    text = stringResource(id = R.string.terms_agree_start),
                    style = Subtitle3_SemiBold,
                    color = Color.White
                )
            }
        )
    }
}

// dimens
private val ScreenTopPadding = 60.dp
private val TermsCharSize = 65.dp
private val TermsItemSpacing = 20.dp
private val ButtonContentPadding = 45.dp


@Preview
@Composable
private fun TermsAgreementScreenPreview() {

}
