package com.aiku.domain.model.group

import com.aiku.domain.model.user.Member

data class Group(
    val id: Long,
    val name: String,
    val members: List<Member>
)