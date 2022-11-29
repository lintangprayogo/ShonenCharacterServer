package com.example.route

import com.example.repo.CharacterRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.searchCharacters() {
    val characterRepo: CharacterRepo by inject()
    get("/search-characters") {
        val name = call.request.queryParameters["name"]
        val response=characterRepo.searchCharacters(name )
        call.respond(message = response, status = HttpStatusCode.OK)
    }
}