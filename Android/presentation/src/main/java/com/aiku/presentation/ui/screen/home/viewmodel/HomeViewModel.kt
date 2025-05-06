package com.aiku.presentation.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.aiku.domain.exception.INVALID_INPUT
import com.aiku.domain.usecase.CreateGroupUseCase
import com.aiku.domain.usecase.group.FetchGroupsUseCase
import com.aiku.domain.usecase.schedule.FetchUserSchedulesUseCase
import com.aiku.presentation.state.group.GroupOverviewState
import com.aiku.presentation.state.group.toGroupOverviewState
import com.aiku.presentation.state.schedule.UserScheduleOverviewState
import com.aiku.presentation.state.schedule.toUserScheduleOverviewState
import com.aiku.presentation.util.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

// 오늘 내 약속, 내 그룹
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUserSchedulesUseCase: FetchUserSchedulesUseCase,
    private val fetchGroupsUseCase: FetchGroupsUseCase,
    private val createGroupUseCase: CreateGroupUseCase
) : ViewModel() {

    /** 오늘 내 약속 **/
    private val _todayUserSchedulesUiState = MutableStateFlow<TodayUserSchedulesUiState>(TodayUserSchedulesUiState.Loading)
    val todayUserSchedulesUiState: StateFlow<TodayUserSchedulesUiState> = _todayUserSchedulesUiState.asStateFlow()

    private val startDate = LocalDate.now().atStartOfDay()
    private val endDate = LocalDate.now().atTime(LocalTime.MAX)

    val userSchedules: Flow<PagingData<UserScheduleOverviewState>> = fetchUserSchedulesUseCase(startDate, endDate, true)
        .map { pagingData ->
            pagingData.map { it.toUserScheduleOverviewState() }
        }
        .cachedIn(viewModelScope)
        .onStart { _todayUserSchedulesUiState.emit(TodayUserSchedulesUiState.Loading) }
        .onEach { _todayUserSchedulesUiState.emit(TodayUserSchedulesUiState.Success) }
        .onError { _todayUserSchedulesUiState.emit(TodayUserSchedulesUiState.Error) }

    /** 내 그룹 **/
    private val _groupsUiState = MutableStateFlow<GroupsUiState>(GroupsUiState.Loading)
    val groupsUiState: StateFlow<GroupsUiState> = _groupsUiState.asStateFlow()

    val groups: Flow<PagingData<GroupOverviewState>> = fetchGroupsUseCase()
        .map { pagingData ->
            pagingData.map { it.toGroupOverviewState() }
        }
        .cachedIn(viewModelScope)
        .onStart { _groupsUiState.emit(GroupsUiState.Loading) }
        .onEach { _groupsUiState.emit(GroupsUiState.Success) }
        .onError { _groupsUiState.emit(GroupsUiState.Error) }

    /** 그룹 생성하기 **/
    // 그룹 이름의 최대 길이 상수
    companion object {
        const val MAX_GROUPNAME_LENGTH = CreateGroupUseCase.MAX_GROUPNAME_LENGTH
    }

    private val _groupNameInput = MutableStateFlow("")
    val groupNameInput: StateFlow<String> = _groupNameInput.asStateFlow()

    private val _isBtnEnabled = MutableStateFlow(false)
    val isBtnEnabled: StateFlow<Boolean> = _isBtnEnabled.asStateFlow()

    private val _createGroupUiState = MutableStateFlow<CreateGroupUiState>(CreateGroupUiState.Idle)
    val createGroupUiState: StateFlow<CreateGroupUiState> = _createGroupUiState

    fun onGroupNameInputChanged(newName: String) {
        _groupNameInput.value = newName
        _isBtnEnabled.value = newName.isNotEmpty()
    }

    fun createGroup() {
        createGroupUseCase(_groupNameInput.value)
            .onStart { _createGroupUiState.emit(CreateGroupUiState.Loading) }
            .onEach { _createGroupUiState.emit(CreateGroupUiState.Success) }
            .onError { error ->
                val uiState = when (error.code) {
                    INVALID_INPUT -> CreateGroupUiState.InvalidInput
                    else -> CreateGroupUiState.ServerError
                }
                _createGroupUiState.emit(uiState)
            }
            .launchIn(viewModelScope)
    }

}

sealed interface TodayUserSchedulesUiState {
    data object Loading : TodayUserSchedulesUiState
    data object Success : TodayUserSchedulesUiState
    data object Error : TodayUserSchedulesUiState
}

sealed interface GroupsUiState {
    data object Loading : GroupsUiState
    data object Success : GroupsUiState
    data object Error : GroupsUiState
}

sealed interface CreateGroupUiState {
    data object Idle : CreateGroupUiState
    data object Loading : CreateGroupUiState
    data object Success : CreateGroupUiState
    data object InvalidInput : CreateGroupUiState
    data object ServerError : CreateGroupUiState
}
