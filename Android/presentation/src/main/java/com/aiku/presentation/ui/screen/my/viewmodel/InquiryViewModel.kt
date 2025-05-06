package com.aiku.presentation.ui.screen.my.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiku.domain.model.user.type.TermType
import com.aiku.domain.repository.UserRepository
import com.aiku.presentation.state.user.TermViewState
import com.aiku.presentation.state.user.TermsViewState
import com.aiku.presentation.state.user.toTermsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    val title: StateFlow<String> = savedStateHandle.getStateFlow(KEY_TITLE, "")
    val content: StateFlow<String> = savedStateHandle.getStateFlow(KEY_CONTENT, "")
    val nickname: StateFlow<String> =
        savedStateHandle.getStateFlow(KEY_NICKNAME, userRepository.fetchUser().map { it.nickname }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ""
            ).value
        )

    private val terms = MutableStateFlow<TermsViewState?>(null)

    fun onTermItemClicked(termType: TermType, onLoaded: (TermViewState) -> Unit) {
        viewModelScope.launch {
            if (terms.value == null)
                terms.value = userRepository.fetchTerms().toTermsViewState()

            onLoaded(terms.value!!.terms.first { it.title == termType })
        }
    }

    private val _isPrivacyAgreed = MutableStateFlow(false)
    val isPrivacyAgreed: StateFlow<Boolean> = _isPrivacyAgreed.asStateFlow()

    fun onTitleChanged(title: String) {
        savedStateHandle[KEY_TITLE] = title
    }

    fun onContentChanged(content: String) {
        if (content.length > MAX_CONTENT_LENGTH) return
        savedStateHandle[KEY_CONTENT] = content
    }

    fun onNicknameChanged(nickname: String) {
        savedStateHandle[KEY_NICKNAME] = nickname
    }

    fun onPrivacyAgreedChanged(isPrivacyAgreed: Boolean) {
        _isPrivacyAgreed.value = isPrivacyAgreed
    }

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_CONTENT = "content"
        private const val KEY_NICKNAME = "nickname"

        const val MAX_CONTENT_LENGTH = 200
    }
}