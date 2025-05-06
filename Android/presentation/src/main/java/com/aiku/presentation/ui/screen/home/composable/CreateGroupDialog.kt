package com.aiku.presentation.ui.screen.home.composable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aiku.core.R
import com.aiku.core.theme.Body1
import com.aiku.core.theme.Body1_SemiBold
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.Typo
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.component.dialog.MinimalDialog
import com.aiku.presentation.ui.component.textfield.BottomLinedTextField
import com.aiku.presentation.ui.screen.home.viewmodel.CreateGroupUiState
import com.aiku.presentation.ui.screen.home.viewmodel.HomeViewModel

@Composable
fun CreateGroupDialog(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit
) {
    val groupNameInput by homeViewModel.groupNameInput.collectAsStateWithLifecycle()
    val isButtonEnabled by homeViewModel.isBtnEnabled.collectAsStateWithLifecycle()
    val createGroupUiState by homeViewModel.createGroupUiState.collectAsStateWithLifecycle()

    MinimalDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.group_name),
                style = Body1_SemiBold,
                color = Typo
            )

            BottomLinedTextField(
                value = groupNameInput,
                onValueChange = homeViewModel::onGroupNameInputChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                maxLines = 1,
                showLengthIndicator = true,
                maxLength = HomeViewModel.MAX_GROUPNAME_LENGTH,
                placeholder = stringResource(id = R.string.group_name_setup_placeholder),
                label = stringResource(id = R.string.group_name_setup_label),
                textStyle = Body1
            )

            FullWidthButton(
                modifier = Modifier.padding(top = 57.dp),
                enabled = isButtonEnabled,
                background = ButtonDefaults.buttonColors(
                    containerColor = Green5,
                    disabledContainerColor = Gray02
                ),
                onClick = { homeViewModel.createGroup() },
                content = {
                    Text(
                        text = stringResource(id = R.string.create),
                        style = Subtitle3_SemiBold,
                        color = Color.White
                    )
                }
            )
        }
    }

    LaunchedEffect(createGroupUiState) {
        when (createGroupUiState) {
            CreateGroupUiState.Success -> {
                onDismissRequest()
            }
            CreateGroupUiState.Loading -> { }
            CreateGroupUiState.InvalidInput -> {}
            CreateGroupUiState.ServerError -> {}
            else -> {}
        }
    }
}

@Preview
@Composable
private fun CreateGroupDialogPreview() {
    CreateGroupDialog(
        onDismissRequest = {}
    )
}
