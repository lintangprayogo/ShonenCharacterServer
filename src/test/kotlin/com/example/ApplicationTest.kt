package com.example


import com.example.models.ApiResponse
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.plugins.*
import com.example.repo.CharacterRepo
import org.koin.java.KoinJavaComponent.inject
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class ApplicationTest {
    private val characterRepo: CharacterRepo by inject(CharacterRepo::class.java)

    companion object {
        private const val PREV_PAGE = "prevPage"
        private const val NEXT_PAGE = "nextPage"
    }

    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(expected = HttpStatusCode.OK, actual = status)
            assertEquals(expected = "Welcome To Api", actual = bodyAsText())
        }
    }

    @Test
    fun `access  get all characters,asserts correct information`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/getAllCharacters").apply {
            assertEquals(expected = HttpStatusCode.OK, actual = status)

            val expected = ApiResponse(
                success = true,
                message = "success",
                prevPage = null,
                nextPage = 2,
                characters = characterRepo.page1
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText())
            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun `access  get all characters,asserts correct information page two`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/getAllCharacters?page=2").apply {
            assertEquals(expected = HttpStatusCode.OK, actual = status)

            val expected = ApiResponse(
                success = true,
                message = "success",
                prevPage = 1,
                nextPage = 3,
                characters = characterRepo.page2
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText())
            assertEquals(expected = expected, actual = actual)
        }
    }


    @Test
    fun `access  get all characters query all pages,asserts correct information `() = testApplication {
        application {
            configureRouting()
        }
        val pages = 1..5
        pages.forEach { page ->

            val calculatePage = calculatePage(page)
            client.get("/getAllCharacters?page=$page").apply {
                assertEquals(expected = HttpStatusCode.OK, actual = status)
                //kelas character baru di inject setelah melakukan pengambilan data
                val list = listOf(
                    characterRepo.page1,
                    characterRepo.page2,
                    characterRepo.page3,
                    characterRepo.page4,
                    characterRepo.page5,
                )

                val expected = ApiResponse(
                    success = true,
                    message = "success",
                    prevPage = calculatePage[PREV_PAGE],
                    nextPage = calculatePage[NEXT_PAGE],
                    characters = list[page - 1]
                )
                val actual = Json.decodeFromString<ApiResponse>(bodyAsText())
                assertEquals(expected = expected, actual = actual)
            }
        }
    }

    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page

        if (page in 1..4) {
            nextPage = nextPage?.plus(1)
        }

        if (page in 2..5) {
            prevPage = prevPage?.minus(1)
        }

        if (page == 1) {
            prevPage = null
        }

        if (page == 5) {
            nextPage = null
        }

        return mapOf(PREV_PAGE to prevPage, NEXT_PAGE to nextPage)
    }

}