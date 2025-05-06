package com.aiku.domain.exception

/** Server-side Error code */
const val UNKNOWN = 0
const val ALREADY_EXIST_NICKNAME = 10002

/** Login - kakao */
const val ERROR_KAKAO_SERVER = 3000 //kakao server
const val ERROR_KAKAO_OIDC = 3001 //open id connect
const val ERROR_KAKAO_USER_EMAIL = 3001 //user email
/** Login - server */
const val ERROR_SERVER_ISSUE_ATRT = 3003 //회원가입 필요
const val ERROR_AUTO_LOGIN = 3004



/** 입력 */
const val INVALID_INPUT = 11006

/** Client-side Error code */
const val TOKEN_NOT_FOUND = 11003
const val REQUIRE_NICKNAME_INPUT = 20000
const val INVALID_NICKNAME_FORMAT = 20002
const val NICKNAME_LENGTH_EXCEED = 20004

/** Schedule */
const val BET_AMOUNT_NOT_POSITIVE = 20100


/** HTTP Status Codes */
//object HttpStatusCodes {
//    const val BAD_REQUEST = 400
//    const val UNAUTHORIZED = 401
//    const val FORBIDDEN = 403
//    const val NOT_FOUND = 404
//    const val REQUEST_TIMEOUT = 408
//
//    const val INTERNAL_SERVER_ERROR = 500
//    const val NOT_IMPLEMENTED = 501
//    const val BAD_GATEWAY = 502
//    const val SERVICE_UNAVAILABLE = 503
//    const val GATEWAY_TIMEOUT = 504
//}