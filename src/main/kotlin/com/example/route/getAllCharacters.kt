package com.example.route

import com.example.models.ApiResponse
import com.example.repo.CharacterRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

import java.lang.NumberFormatException

fun Route.getAllCharacters() {
    val characterRepo: CharacterRepo by inject()

    get("/getAllCharacters") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)
            val apiResponse = characterRepo.getCharacters(page)
            call.respond(
                message = apiResponse,
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(message = "Only Number Allowed", success = false),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(message = "Characters Not Found", success = false),
                status = HttpStatusCode.BadRequest
            )
        }

    }
}