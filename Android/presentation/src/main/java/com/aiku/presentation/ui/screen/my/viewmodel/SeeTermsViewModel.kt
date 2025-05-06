package com.aiku.presentation.ui.screen.my.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiku.domain.model.user.type.TermType
import com.aiku.domain.repository.UserRepository
import com.aiku.presentation.state.user.TermViewState
import com.aiku.presentation.state.user.TermsViewState
import com.aiku.presentation.state.user.toTermsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeTermsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val terms = MutableStateFlow<TermsViewState?>(null)

    fun onTermItemClicked(termType: TermType, onLoaded: (TermViewState) -> Unit) {
        viewModelScope.launch {
            if (terms.value == null)
                terms.value = userRepository.fetchTerms().toTermsViewState()

            onLoaded(terms.value!!.terms.first { it.title == termType })
        }
    }
}