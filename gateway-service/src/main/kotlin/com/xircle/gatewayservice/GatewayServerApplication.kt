package com.xircle.gatewayservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@EnableFeignClients
@ComponentScan(
    basePackages = [
        "com.xircle.gatewayservice",
        "com.xircle.common"
    ]
)
@SpringBootApplication
class GatewayServerApplication

fun main(args: Array<String>) {
    runApplication<GatewayServerApplication>(*args)
}