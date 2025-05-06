package com.aiku.domain.usecase

import com.aiku.domain.model.group.Group
import com.aiku.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExitGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {

    operator fun invoke(group: Group): Flow<Unit> {
        return flow {
            TODO("실행 중인 약속있는지 확인 하고 예외처리, Group -> Schedule")
            emit(Unit)
        }.flatMapLatest {
            groupRepository.exitGroup(group.id)
        }
    }
}