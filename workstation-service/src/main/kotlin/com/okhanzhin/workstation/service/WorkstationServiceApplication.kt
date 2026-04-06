package com.okhanzhin.workstation.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope

@SpringBootApplication
@RefreshScope
class WorkstationServiceApplication

fun main(args: Array<String>) {
    runApplication<WorkstationServiceApplication>(*args)
}

