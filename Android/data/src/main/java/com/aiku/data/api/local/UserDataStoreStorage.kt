package com.aiku.data.api.local

import androidx.datastore.core.DataStore
import com.aiku.data.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataStoreStorage @Inject constructor(
    private val userDataStore: DataStore<UserEntity>
) {

    suspend fun saveUser(user: UserEntity) {
        userDataStore.updateData {
            it.toBuilder()
                .setMemberId(user.memberId)
                .setNickname(user.nickname)
                .setKakaoId(user.kakaoId)
                .setProfile(user.profile)
                .setBadge(user.badge)
                .setPoint(user.point)
                .build()
        }
    }
}
