package com.aiku.presentation.ui.screen.schedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.aiku.domain.usecase.schedule.FetchUserSchedulesUseCase
import com.aiku.presentation.state.schedule.UserScheduleOverviewState
import com.aiku.presentation.state.schedule.toUserScheduleOverviewState
import com.aiku.presentation.ui.screen.home.viewmodel.TodayUserSchedulesUiState
import com.aiku.presentation.util.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val fetchUserSchedulesUseCase: FetchUserSchedulesUseCase
) : ViewModel() {

    /** 현재 년도와 월 **/
    private val _currentYearMonth = MutableStateFlow(YearMonth.now())
    val currentYearMonth: StateFlow<YearMonth> = _currentYearMonth.asStateFlow()

    private val _userMonthlySchedulesUiState = MutableStateFlow<UserMonthlySchedulesUiState>(UserMonthlySchedulesUiState.Loading)
    val userMonthlySchedulesUiState: StateFlow<UserMonthlySchedulesUiState> = _userMonthlySchedulesUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val userMonthlySchedules: Flow<PagingData<UserScheduleOverviewState>> = _currentYearMonth
        .filterNotNull()
        .flatMapLatest { yearMonth ->
            val startDate = yearMonth.atDay(1).atStartOfDay()
            val endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59)

            fetchUserSchedulesUseCase(startDate, endDate, false)
                .map { pagingData ->
                    pagingData.map { it.toUserScheduleOverviewState() }
                }
                .onStart { _userMonthlySchedulesUiState.emit(UserMonthlySchedulesUiState.Loading) }
                .onEach { _userMonthlySchedulesUiState.emit(UserMonthlySchedulesUiState.Success) }
                .onError { _userMonthlySchedulesUiState.emit(UserMonthlySchedulesUiState.Error) }
                .cachedIn(viewModelScope)
        }


    /** 이전 달로 이동 **/
    fun onPreviousMonth() {
        viewModelScope.launch {
            _currentYearMonth.value = _currentYearMonth.value.minusMonths(1)
        }
    }

    /** 다음 달로 이동 **/
    fun onNextMonth() {
        viewModelScope.launch {
            _currentYearMonth.value = _currentYearMonth.value.plusMonths(1)
        }
    }

    /** 선택된 날짜 약속 **/
    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    private val _userSchedulesUiState = MutableStateFlow<UserSchedulesUiState>(UserSchedulesUiState.Loading)
    val userSchedulesUiState: StateFlow<UserSchedulesUiState> = _userSchedulesUiState.asStateFlow()

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userSchedules: Flow<PagingData<UserScheduleOverviewState>> = _selectedDate
        .filterNotNull()
        .flatMapLatest { date ->
            val startDate = date.atStartOfDay()
            val endDate = date.atTime(23, 59, 59)
            fetchUserSchedulesUseCase(startDate, endDate, false)
                .map { pagingData ->
                    pagingData.map { it.toUserScheduleOverviewState() }
                }
                .cachedIn(viewModelScope)
                .onStart { _userSchedulesUiState.emit(UserSchedulesUiState.Loading) }
                .onEach { _userSchedulesUiState.emit(UserSchedulesUiState.Success) }
                .onError { _userSchedulesUiState.emit(UserSchedulesUiState.Error) }
        }
}

sealed interface UserSchedulesUiState {
    data object Loading : UserSchedulesUiState
    data object Success : UserSchedulesUiState
    data object Error : UserSchedulesUiState
}

sealed interface UserMonthlySchedulesUiState {
    data object Loading : UserMonthlySchedulesUiState
    data object Success : UserMonthlySchedulesUiState
    data object Error : UserMonthlySchedulesUiState
}

