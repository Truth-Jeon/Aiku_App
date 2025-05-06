package com.aiku.presentation.base

import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.repository.UserRepository
import com.aiku.presentation.state.user.BadgeState
import com.aiku.presentation.state.user.ProfileState
import com.aiku.presentation.state.user.UserState
import com.aiku.presentation.state.user.toUserState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

interface UserDataProvider {
    val user: StateFlow<UserState>
}

class UserDataProviderImpl @Inject constructor(
    userRepository: UserRepository,
    coroutineScope: CoroutineScope
) : UserDataProvider {

    override val user: StateFlow<UserState> = userRepository.fetchUser().map {
        it.toUserState()
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = UserState(
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
}