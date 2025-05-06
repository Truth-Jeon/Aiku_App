package com.aiku.presentation.ui.screen.my.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aiku.core.R
import com.aiku.core.theme.Body1_SemiBold
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.domain.model.user.type.TermType
import com.aiku.presentation.state.user.TermViewState
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.component.textfield.FilledTextField
import com.aiku.presentation.ui.component.topbar.DefaultTopAppBar
import com.aiku.presentation.ui.screen.my.viewmodel.InquiryViewModel
import com.aiku.presentation.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquiryScreen(
    modifier: Modifier = Modifier,
    onNavigateTermDetailScreen: (TermViewState) -> Unit = {},
    viewModel: InquiryViewModel = hiltViewModel()
) {

    val (first, second, third, fourth) = remember { FocusRequester.createRefs() }

    val titleInput by viewModel.title.collectAsStateWithLifecycle()
    val contentInput by viewModel.content.collectAsStateWithLifecycle()
    val nicknameInput by viewModel.nickname.collectAsStateWithLifecycle()

    val isPrivacyAgreed by viewModel.isPrivacyAgreed.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        DefaultTopAppBar(
            title = stringResource(R.string.item_inquiry)
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(R.string.how_to_help),
            style = Body1_SemiBold
        )

        FilledTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp).focusRequester(first),
            value = titleInput,
            onValueChange = viewModel::onTitleChanged,
            placeholder = stringResource(R.string.input_inquiry_title),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { second.requestFocus() }
            )
        )

        FilledTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).focusRequester(second).height(
                200.dp
            ),
            value = contentInput,
            onValueChange = viewModel::onContentChanged,
            placeholder = stringResource(R.string.input_inquiry_content),
            showTextCounter = true,
            maxLength = InquiryViewModel.MAX_CONTENT_LENGTH,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { third.requestFocus() }
            )
        )

        Text(
            modifier = Modifier.padding(top = 44.dp),
            text = stringResource(R.string.check_user_information),
            style = Body1_SemiBold
        )

        FilledTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp).focusRequester(third),
            value = nicknameInput,
            onValueChange = viewModel::onNicknameChanged,
            placeholder = stringResource(R.string.user_name),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { fourth.requestFocus() }
            )
        )

        FilledTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).focusRequester(fourth),
            value = titleInput, // TODO 이메일
            onValueChange = viewModel::onTitleChanged,
            placeholder = stringResource(R.string.input_inquiry_title),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { fourth.freeFocus() }
            )
        )

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isPrivacyAgreed,
                onCheckedChange = viewModel::onPrivacyAgreedChanged,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    checkmarkColor = Color.White
                )
            )
            Row(
                modifier = Modifier.noRippleClickable {
                    viewModel.onTermItemClicked(TermType.PERSONALINFO) {
                        onNavigateTermDetailScreen(it)
                    }
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.terms_item_collect_privacy),
                    style = Body2
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }

        FullWidthButton(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            background = ButtonDefaults.buttonColors(
                containerColor = Green5
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "입력 완료",
                style = Subtitle3_SemiBold
            )
        }
    }
}