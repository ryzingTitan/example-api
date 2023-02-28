package com.example.api.domain.dtos

import lombok.Generated

@Generated
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val fullName: String,
)
