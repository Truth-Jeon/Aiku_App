package com.aiku.domain.exception

import java.io.IOException

class ClientNetworkException(
    val statusCode: Int
): IOException()

class ServerNetworkException(
    val statusCode: Int
): IOException()