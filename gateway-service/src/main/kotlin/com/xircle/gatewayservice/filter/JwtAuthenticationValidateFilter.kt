package com.xircle.gatewayservice.filter

import com.xircle.gatewayservice.api.client.UserServiceClientAdapter
import com.xircle.gatewayservice.auth.TokenService
import com.xircle.gatewayservice.response.ResponseUtil
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationValidateFilter(
    private val tokenService: TokenService,
    private val userServiceClientAdapter: UserServiceClientAdapter,
) : WebFilter {
    companion object {
        private const val AUTH_HEADER = "Authorization"
        private const val HEADER_NAME = "memberId"
        private const val CLAIM_ID = "id"
    }

    private val publicPaths = arrayOf("/user-service/member", "/user-service/login")

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request: ServerHttpRequest = exchange.request
        val path = request.uri.path
        publicPaths.forEach {
            if (path.startsWith(it)) {
                return chain.filter(exchange)
            }
        }

        val token: String? = request.headers.getFirst(AUTH_HEADER)
        if (token.isNullOrEmpty()) {
            return ResponseUtil.createAuthenticationErrorResponse(exchange)
        }

        return Mono.fromCallable {
                tokenService.verifyToken(token)
                tokenService.decodeToken(token)
            }
            .flatMap { decodedToken ->
                val memberId = decodedToken.getClaim(CLAIM_ID).asLong()
                userServiceClientAdapter.getMemberInfo(memberId.toLong())
            }
            .flatMap { member ->
                val modifiedRequest = exchange.request.mutate()
                    .header(HEADER_NAME, member.id.toString())
                    .build()
                val modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build()
                chain.filter(modifiedExchange)
            }
            .onErrorResume {
                ResponseUtil.createAuthenticationErrorResponse(exchange)
            }
    }
}