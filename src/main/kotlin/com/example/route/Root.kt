package com.example.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.root() {
    get("/") {
        call.respond(message = "Welcome To Api", status = HttpStatusCode.OK)
    }
}