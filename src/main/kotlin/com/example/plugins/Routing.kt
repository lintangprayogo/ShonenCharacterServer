package com.example.plugins

import com.example.route.getAllCharacters
import com.example.route.root
import com.example.route.searchCharacters
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*


fun Application.configureRouting() {

    routing {
        root()
        getAllCharacters()
        searchCharacters()
        static("/images") {
            resources("images")
        }
    }
}
