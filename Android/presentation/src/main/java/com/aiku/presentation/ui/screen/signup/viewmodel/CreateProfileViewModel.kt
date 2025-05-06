package com.aiku.presentation.ui.screen.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiku.domain.exception.ALREADY_EXIST_NICKNAME
import com.aiku.domain.exception.INVALID_NICKNAME_FORMAT
import com.aiku.domain.exception.NICKNAME_LENGTH_EXCEED
import com.aiku.domain.exception.REQUIRE_NICKNAME_INPUT
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.usecase.SaveUserUseCase
import com.aiku.presentation.base.UserDataProvider
import com.aiku.presentation.state.user.BadgeState
import com.aiku.presentation.state.user.ProfileState
import com.aiku.presentation.state.user.UserState
import com.aiku.presentation.util.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveUserUseCase: SaveUserUseCase,
    userDataProvider: UserDataProvider
): ViewModel(), UserDataProvider by userDataProvider {

    val profileInput = savedStateHandle.getStateFlow(
        PROFILE_INPUT,
        UserState(
            id = 0,
            nickname = "",
            kakaoId = "",
            profile = ProfileState(
                type = ProfileType.CHAR,
                image = "",
                character = ProfileCharacter.C01,
                background = ProfileBackground.GREEN
            ),
            badge = BadgeState(
                name = "",
                image = ""
            ),
            point = 0
        )
    )

    private val _saveProfileUiState = MutableStateFlow<SaveProfileUiState>(SaveProfileUiState.Idle)
    val saveProfileUiState: StateFlow<SaveProfileUiState> =
        _saveProfileUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SaveProfileUiState.Idle
        )

    fun saveProfile() {
        saveUserUseCase(profileInput.value.toUser()).onStart {
            _saveProfileUiState.emit(SaveProfileUiState.Loading)
        }.map {
            _saveProfileUiState.emit(SaveProfileUiState.Success)
        }.onError {
            when (it.code) {
                REQUIRE_NICKNAME_INPUT -> _saveProfileUiState.emit(SaveProfileUiState.RequireNicknameInput)
                ALREADY_EXIST_NICKNAME -> _saveProfileUiState.emit(SaveProfileUiState.AlreadyExistNickname)
                INVALID_NICKNAME_FORMAT -> _saveProfileUiState.emit(SaveProfileUiState.InvalidNicknameFormat)
                NICKNAME_LENGTH_EXCEED -> _saveProfileUiState.emit(SaveProfileUiState.NicknameLengthExceed)
                else -> _saveProfileUiState.emit(SaveProfileUiState.Idle)
            }
        }.launchIn(viewModelScope)
    }

    fun onNicknameInputChanged(input: String) {
        onProfileInputChanged(profileInput.value.copy(nickname = input))
    }

    fun onImageInputChanged(input: String) {
        //onProfileInputChanged(profileInput.value.copy(image = input))
    }

    private fun onProfileInputChanged(user: UserState) {
        savedStateHandle[PROFILE_INPUT] = user
        _saveProfileUiState.value = SaveProfileUiState.Idle
    }

    companion object {
        const val MAX_NICKNAME_LENGTH = SaveUserUseCase.MAX_NICKNAME_LENGTH
        private const val PROFILE_INPUT = "profile_input"
    }
}

sealed interface SaveProfileUiState {
    data object Idle : SaveProfileUiState
    data object Loading : SaveProfileUiState
    data object Success : SaveProfileUiState
    data object RequireNicknameInput : SaveProfileUiState
    data object AlreadyExistNickname : SaveProfileUiState
    data object InvalidNicknameFormat : SaveProfileUiState
    data object NicknameLengthExceed : SaveProfileUiState
}