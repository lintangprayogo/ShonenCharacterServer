package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Long,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val strength: Int,
    val month: String,
    val day: String,
    val related: List<String>,
    val elements: List<String>,
    val skillSet: List<String>
)
