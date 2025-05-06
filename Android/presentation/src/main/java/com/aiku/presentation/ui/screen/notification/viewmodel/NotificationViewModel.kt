package com.aiku.presentation.ui.screen.notification.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiku.domain.model.notification.Notification
import com.aiku.domain.model.notification.NotificationCategory
import com.aiku.domain.repository.NotificationRepository
import com.aiku.presentation.state.notification.NotificationViewState
import com.aiku.presentation.state.notification.toNotificationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val notificationRepository: NotificationRepository
): ViewModel() {

    private val notificationCategory = savedStateHandle.getStateFlow(KEY_NOTIFICATION_CATEGORY, "")

    val notificationUiState = notificationRepository
        .getNotifications().combine(notificationCategory) {
            notifications, category ->
            if (category == "ALL") {
                notifications
            } else {
                notifications.filter { it.category.name == category }
            }
        }.map<List<Notification>, NotificationUiState> {
            if (it.isEmpty()) {
                NotificationUiState.Empty
            } else {
                NotificationUiState.Success(it.map { notification -> notification.toNotificationViewState() })
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotificationUiState.Loading
        )

    fun setNotificationCategory(category: String) {
        savedStateHandle[KEY_NOTIFICATION_CATEGORY] = category
    }

    companion object {
        private const val KEY_NOTIFICATION_CATEGORY = "notification_category"
    }
}

sealed interface NotificationUiState {
    data object Loading: NotificationUiState
    data class Success(val notifications: List<NotificationViewState>): NotificationUiState
    data object Empty: NotificationUiState
}