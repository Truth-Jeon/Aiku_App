package com.aiku.presentation.ui.screen.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiku.domain.usecase.ReadTermsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    private val readTermsUseCase: ReadTermsUseCase
) : ViewModel() {

    private val _termsUiState = MutableStateFlow<LoadTermsUiState>(LoadTermsUiState.Idle)
    val termsUiState: StateFlow<LoadTermsUiState> =
        _termsUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LoadTermsUiState.Idle
        )

    // 체크박스 상태 관리
    private val _checkedStates = MutableStateFlow(listOf(false, false, false, false))
    val checkedStates: StateFlow<List<Boolean>> = _checkedStates

    // 모든 체크박스가 체크되었는지 확인
    val checkedAll: StateFlow<Boolean> = _checkedStates.map { it.all { checked -> checked } }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    // 버튼 활성화 로직
    val btnEnabled: StateFlow<Boolean> = _checkedStates.map { states ->
        states[0] && states[1] && states[2] || states.all { it }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    // 체크 상태 변경 로직
    fun onCheckedChanged(index: Int, isChecked: Boolean) {
        val updatedStates = _checkedStates.value.toMutableList()
        if (index == -1) {
            // "전체 동의" 클릭 시 모든 항목을 같은 상태로 변경
            for (i in updatedStates.indices) {
                updatedStates[i] = isChecked
            }
        } else {
            // 개별 항목 클릭 시 해당 항목만 업데이트
            updatedStates[index] = isChecked
        }
        _checkedStates.value = updatedStates
    }

    fun loadTerms(identifier: Int) {
        readTermsUseCase.execute(identifier)
            .onStart {
                _termsUiState.emit(LoadTermsUiState.Loading)
            }
            .onEach { result ->
                val uiState = when {
                    result.isSuccess -> {
                        val content = result.getOrNull()
                        if (!content.isNullOrEmpty()) {
                            LoadTermsUiState.Success(content)
                        } else {
                            LoadTermsUiState.Failure("파일 내용이 비어 있습니다.")
                        }
                    }
                    result.isFailure -> {
                        val exception = result.exceptionOrNull()
                        LoadTermsUiState.Failure(exception?.message ?: "알 수 없는 오류 발생")
                    }
                    else -> LoadTermsUiState.Failure("알 수 없는 오류 발생")
                }
                _termsUiState.emit(uiState)
            }
            .catch { e ->
                val errorMessage = when (e) {
                    is FileNotFoundException -> "파일을 찾을 수 없습니다."
                    is IOException -> "입출력 오류 발생"
                    else -> "알 수 없는 오류 발생"
                }
                _termsUiState.emit(LoadTermsUiState.Failure(errorMessage))
            }
            .launchIn(viewModelScope)
    }


}

sealed interface LoadTermsUiState {
    data object Idle : LoadTermsUiState
    data object Loading : LoadTermsUiState
    data class Success(val content: String) : LoadTermsUiState
    data class Failure(val message: String) : LoadTermsUiState
}

