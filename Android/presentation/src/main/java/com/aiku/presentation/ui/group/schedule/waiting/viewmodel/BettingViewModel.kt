package com.aiku.presentation.ui.group.schedule.waiting.viewmodel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aiku.domain.exception.BET_AMOUNT_NOT_POSITIVE
import com.aiku.domain.usecase.BetUseCase
import com.aiku.presentation.navigation.GroupNavType
import com.aiku.presentation.navigation.MemberNavType
import com.aiku.presentation.navigation.route.Routes
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.util.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class BettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val betUseCase: BetUseCase
) : ViewModel() {

    private val arguments = savedStateHandle.toRoute<Routes.ScheduleWaiting.Betting>(
        mapOf(
            typeOf<MemberState>() to MemberNavType,
            typeOf<GroupState>() to GroupNavType
        )
    )
    val member = arguments.member
    val group = arguments.group
    val scheduleId = arguments.scheduleId

    val akuInput = savedStateHandle.getStateFlow(AKU, "")

    private val _bettingUiState = MutableSharedFlow<BettingUiState>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val bettingUiState: SharedFlow<BettingUiState> = _bettingUiState.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    fun onBetAkuChanged(betAku: String) {
        if (betAku.isDigitsOnly()) {
            savedStateHandle[AKU] = (betAku.toIntOrNull() ?: "").toString()
        }
    }

    fun bet() {
        betUseCase(
            scheduleId = scheduleId,
            beteeMemberId = member.id,
            pointAmount = akuInput.value.toIntOrNull() ?: 0
        ).onEach {
            _bettingUiState.emit(BettingUiState.Success)
        }.onError {
            when(it.code) {
                BET_AMOUNT_NOT_POSITIVE -> {
                    _bettingUiState.emit(BettingUiState.BetAmountNotPositive)
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        const val AKU = "aku"
    }
}

sealed interface BettingUiState {
    data object Loading : BettingUiState
    data object Success : BettingUiState
    data object BetAmountNotPositive : BettingUiState
}