package com.aiku.presentation.ui.screen.signup.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.aiku.core.R
import com.aiku.core.theme.Headline2
import com.aiku.core.theme.Subtitle3
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.background.CircularBackground
import com.aiku.presentation.ui.component.background.CircularBorderBackground
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.component.dialog.DefaultCharacterDialog
import com.aiku.presentation.ui.component.dialog.MinimalDialog
import com.aiku.presentation.ui.component.textfield.BottomLinedTextField
import com.aiku.presentation.ui.screen.signup.viewmodel.CreateProfileViewModel
import com.aiku.presentation.ui.screen.signup.viewmodel.SaveProfileUiState
import com.aiku.presentation.util.asImageBitmap
import com.aiku.presentation.util.parseImageBitmap
import com.aiku.presentation.util.takePhotoFromAlbum
import com.aiku.presentation.util.takePhotoFromAlbumIntent

@Composable
fun ProfileEditScreen(
    idToken : String,
    email : String?,
    modifier: Modifier = Modifier,
    viewModel: CreateProfileViewModel = hiltViewModel(),
    onCompleteEdit: () -> Unit
) {

    val context = LocalContext.current

    val saveProfileUiState by viewModel.saveProfileUiState.collectAsStateWithLifecycle()

    val profileInput by viewModel.profileInput.collectAsStateWithLifecycle()
    var nicknameTextFieldLabel by remember { mutableStateOf("") }

    var showDialogNav by remember { mutableStateOf(false) }
    val dialogNavController = rememberNavController()

    var selectedDefaultCharacter by remember { mutableIntStateOf(R.drawable.char_head_boy) }
    var selectedBackgroundColor by remember { mutableStateOf(Green5) }
    var selectedImageBitmap by remember {
        mutableStateOf(
            (R.drawable.char_head_boy).asImageBitmap(context)
        )
    }

    val takePhotoFromAlbumLauncher = takePhotoFromAlbum(onResult = { uri ->
        selectedImageBitmap = uri.parseImageBitmap(context)
    })


    Column(
        modifier = modifier,
    ) {
        if (showDialogNav) {
            NavHost(
                navController = dialogNavController,
                startDestination = ProfileImageDialogRoute.SelectWay.name
            ) {
                dialog(ProfileImageDialogRoute.SelectWay.name) {
                    MinimalDialog(onDismissRequest = { showDialogNav = false }) {
                        Text(
                            text = stringResource(id = R.string.select_using_default_character),
                            textAlign = TextAlign.Center,
                            style = Subtitle3,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    dialogNavController.navigate(ProfileImageDialogRoute.DefaultCharacter.name)
                                }
                                .padding(vertical = 16.dp)
                        )
                        HorizontalDivider()
                        Text(text = stringResource(id = R.string.select_using_album),
                            textAlign = TextAlign.Center,
                            style = Subtitle3, modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                                    showDialogNav = false
                                }
                                .padding(vertical = 16.dp))
                    }
                }
                dialog(ProfileImageDialogRoute.DefaultCharacter.name) {
                    DefaultCharacterDialog(
                        onDismissRequest = {
                            showDialogNav = false
                            dialogNavController.popBackStack(
                                ProfileImageDialogRoute.SelectWay.name,
                                inclusive = false,
                                saveState = false
                            )
                        },
                        currentCharacterRes = selectedDefaultCharacter,
                        currentBackgroundColor = selectedBackgroundColor,
                        onComplete = { characterRes, backgroundColor ->

                            selectedDefaultCharacter = characterRes
                            selectedBackgroundColor = backgroundColor

                            showDialogNav = false
                            dialogNavController.popBackStack(
                                ProfileImageDialogRoute.SelectWay.name,
                                inclusive = false,
                                saveState = false
                            )
                        }
                    )
                }
            }

        }

        Text(
            text = stringResource(id = R.string.profile_setup_instruction),
            style = Headline2
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.BottomEnd
            ) {
                CircularBackground(
                    modifier = Modifier.size(140.dp),
                    onClick = {
                        showDialogNav = true
                    },
                    color = selectedBackgroundColor,
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        bitmap = selectedImageBitmap,
                        contentDescription = stringResource(id = R.string.profile_image_setup_content_description)
                    )
                }
                Box(
                ) {
                    CircularBorderBackground(
                        modifier = Modifier.size(30.dp),
                        color = Color.White,
                        borderWidth = 0.5.dp,
                        borderColor = Gray02,
                        onClick = {
                            showDialogNav = true
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = stringResource(id = R.string.profile_image_setup_content_description)
                        )
                    }
                }
            }
        }

        BottomLinedTextField(
            value = profileInput.nickname,
            onValueChange = viewModel::onNicknameInputChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 43.dp),
            singleLine = true,
            showLengthIndicator = true,
            maxLength = CreateProfileViewModel.MAX_NICKNAME_LENGTH,
            placeholder = stringResource(id = R.string.profile_nickname_setup_placeholder),
            label = stringResource(id = R.string.profile_nickname_setup_label),
        )

        Spacer(modifier = Modifier.weight(1f))
        FullWidthButton(
            background = ButtonDefaults.buttonColors(containerColor = Green5),
            content = {
                Text(text = stringResource(id = R.string.next))
            }, onClick = onCompleteEdit
        )
    }

    when (saveProfileUiState) {
        is SaveProfileUiState.Loading -> {
            // 대충 로딩뷰
        }

        is SaveProfileUiState.Success -> {
            // 대충 프로필 변경 성공
            // 다음 페이지로 이동
        }

        is SaveProfileUiState.AlreadyExistNickname -> {
            nicknameTextFieldLabel = stringResource(id = R.string.already_exist_nickname)
        }

        is SaveProfileUiState.InvalidNicknameFormat -> {
            // 대충 잘못된 닉네임 형식일 때 UI
        }

        is SaveProfileUiState.NicknameLengthExceed -> {
            // 대충 닉네임 길이 초과일 때 UI
        }

        SaveProfileUiState.RequireNicknameInput -> TODO()
        is SaveProfileUiState.Idle -> {
            nicknameTextFieldLabel = stringResource(id = R.string.profile_nickname_setup_label)
        }
    }

    LaunchedEffect(key1 = selectedDefaultCharacter) {
        selectedImageBitmap = selectedDefaultCharacter.asImageBitmap(context)
    }
}

enum class ProfileImageDialogRoute {
    SelectWay,
    DefaultCharacter
}