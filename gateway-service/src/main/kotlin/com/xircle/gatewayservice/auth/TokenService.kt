package com.xircle.gatewayservice.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWT.decode
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TokenService(
    @Value("\${jwt.secret}") private val jwtSecret: String
) {
    fun verifyToken(jwtToken: String) {
        JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC512(jwtSecret))
            .build()
            .verify(jwtToken)
    }

    fun decodeToken(jwtToken: String): DecodedJWT {
        return decode(jwtToken)
    }
}