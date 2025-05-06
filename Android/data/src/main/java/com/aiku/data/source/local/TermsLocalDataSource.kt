package com.aiku.data.source.local

import android.content.Context
import androidx.annotation.RawRes
import java.io.InputStream
import java.io.InputStreamReader
import java.io.IOException
import java.io.FileNotFoundException
import javax.inject.Inject

class TermsLocalDataSource @Inject constructor(
    private val context: Context
) {
    fun readTermsFromRaw(@RawRes resId: Int): Result<String?> {
        return try {
            val inputStream: InputStream = context.resources.openRawResource(resId)
            val reader = InputStreamReader(inputStream)
            val content = reader.readText()
            reader.close()
            if (content.isNotEmpty()) {
                Result.success(content)
            } else {
                Result.success(null) // 내용이 비어있는 경우
            }
        } catch (e: FileNotFoundException) {
            Result.failure(FileNotFoundException("파일을 찾을 수 없음"))
        } catch (e: IOException) {
            Result.failure(IOException("입출력 오류 발생", e))
        } catch (e: Exception) {
            Result.failure(Exception("알 수 없는 오류 발생", e))
        }
    }
}
