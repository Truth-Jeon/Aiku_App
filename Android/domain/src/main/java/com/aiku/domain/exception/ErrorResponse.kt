package com.aiku.domain.exception

open class ErrorResponse(
    open val code: Int,
    override val message: String = "",
    open val requestId: String = ""
) : Exception()

data class TokenIssueErrorResponse(
    override val code: Int,
    override val message: String = "",
    override val requestId: String = "",
    val idToken: String,
    val email: String?
) : ErrorResponse(code, message, requestId)