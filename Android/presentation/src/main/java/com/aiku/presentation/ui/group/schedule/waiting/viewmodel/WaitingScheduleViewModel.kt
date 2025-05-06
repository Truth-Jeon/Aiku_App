package com.aiku.presentation.ui.group.schedule.waiting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiku.domain.model.schedule.Schedule
import com.aiku.domain.repository.ScheduleRepository
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.state.schedule.ScheduleState
import com.aiku.presentation.state.schedule.toGroupScheduleState
import com.aiku.presentation.util.onError
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = WaitingScheduleViewModel.Factory::class)
class WaitingScheduleViewModel @AssistedInject constructor(
    @Assisted("group") private val group: GroupState,
    @Assisted("scheduleOverview") private val schedule: GroupScheduleOverviewState,
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    val scheduleUiState: StateFlow<ScheduleUiState> = scheduleRepository.fetchGroupScheduleDetail(
        groupId = group.id,
        scheduleId = schedule.id
    ).map<Schedule, ScheduleUiState> {
        ScheduleUiState.Success(it.toGroupScheduleState())
    }.onError {
        emit(ScheduleUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ScheduleUiState.Loading
    )

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("group") group: GroupState,
            @Assisted("scheduleOverview") scheduleOverview: GroupScheduleOverviewState
        ): WaitingScheduleViewModel
    }
}

sealed interface ScheduleUiState {
    data object Loading : ScheduleUiState
    data class Success(val schedule: ScheduleState) : ScheduleUiState
    data object Error : ScheduleUiState
}