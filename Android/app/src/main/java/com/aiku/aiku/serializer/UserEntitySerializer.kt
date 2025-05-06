package com.aiku.aiku.serializer

import androidx.datastore.core.Serializer
import com.aiku.data.UserEntity
import java.io.InputStream
import java.io.OutputStream

object UserEntitySerializer : Serializer<UserEntity> {
    override val defaultValue: UserEntity = UserEntity.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserEntity {
        try {
            return UserEntity.parseFrom(input)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun writeTo(t: UserEntity, output: OutputStream) {
        t.writeTo(output)
    }
}