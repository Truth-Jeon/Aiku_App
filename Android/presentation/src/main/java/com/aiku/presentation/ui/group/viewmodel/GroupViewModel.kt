package com.aiku.presentation.ui.group.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiku.domain.model.group.Group
import com.aiku.domain.repository.GroupRepository
import com.aiku.domain.repository.ScheduleRepository
import com.aiku.domain.usecase.CreateScheduleUseCase
import com.aiku.domain.usecase.ExitGroupUseCase
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.group.toGroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewPaginationState
import com.aiku.presentation.state.schedule.toGroupScheduleOverviewPaginationState
import com.aiku.presentation.util.onError
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

@HiltViewModel(assistedFactory = GroupViewModel.Factory::class)
class GroupViewModel @AssistedInject constructor(
    @Assisted("groupId") private val groupId: Long,
    private val savedStateHandle: SavedStateHandle,
    private val groupRepository: GroupRepository,
    private val scheduleRepository: ScheduleRepository,
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val exitGroupUseCase: ExitGroupUseCase
) : ViewModel() {

    val groupUiState: StateFlow<GroupUiState> = groupRepository.fetchGroup(groupId)
        .map<Group, GroupUiState> { GroupUiState.Success(it.toGroupState()) }
        .onError { emit(GroupUiState.Error(it.message)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GroupUiState.Loading
        )

    private val schedulesPage = savedStateHandle.getStateFlow(PAGE_OF_SCHEDULES, 1)
    val scheduleOverviewUiState: StateFlow<ScheduleOverviewUiState> = schedulesPage.flatMapLatest {
        scheduleRepository.fetchGroupSchedules(groupId, it, LocalDateTime.MIN, LocalDateTime.MAX)
    }.map {
        ScheduleOverviewUiState.Success(it.toGroupScheduleOverviewPaginationState())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ScheduleOverviewUiState.Loading
    )

    private val _exitGroupUiState = MutableSharedFlow<ExitGroupUiState>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val exitGroupUiState = _exitGroupUiState.asSharedFlow()

    fun exitGroup(groupState: GroupState) {
        exitGroupUseCase(groupState.toGroup()).onStart {
            _exitGroupUiState.emit(ExitGroupUiState.Loading)
        }.onEach {
            _exitGroupUiState.emit(ExitGroupUiState.Success)
        }.onError {
            TODO("에러 분기")
        }.launchIn(viewModelScope)
    }

    fun onSchedulePageChanged(page: Int) {
        // TODO : startDate, endDate가 왜 필요?
        scheduleRepository.fetchGroupSchedules(groupId, page, LocalDateTime.MIN, LocalDateTime.MIN)
        savedStateHandle[PAGE_OF_SCHEDULES] = page
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("groupId") groupId: Long): GroupViewModel
    }

    companion object {
        private const val PAGE_OF_SCHEDULES = "page_of_schedules"
        private const val SCHEDULE_OVERVIEWS = "schedule_overviews"
    }
}

sealed interface GroupUiState {
    data object Loading : GroupUiState
    data class Success(val group: GroupState) : GroupUiState
    data class Error(val message: String) : GroupUiState
}

sealed interface ScheduleOverviewUiState {
    data object Loading: ScheduleOverviewUiState
    data class Success(val scheduleOverviewPagination: GroupScheduleOverviewPaginationState): ScheduleOverviewUiState
}

sealed interface ExitGroupUiState {
    data object Loading : ExitGroupUiState
    data object RunningScheduleExist : ExitGroupUiState
    data object Success : ExitGroupUiState
}