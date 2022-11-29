package com.example.repo

import com.example.models.ApiResponse
import com.example.models.Character

interface CharacterRepo {
    val characteres: Map<Int, List<Character>>

    val page1: List<Character>
    val page2: List<Character>
    val page3: List<Character>
    val page4: List<Character>
    val page5: List<Character>

    fun getCharacters(page:Int):ApiResponse

    fun searchCharacters(search:String?):ApiResponse

}