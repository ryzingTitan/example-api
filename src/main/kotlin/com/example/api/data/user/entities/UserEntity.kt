package com.example.api.data.user.entities

import lombok.Generated
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Generated
@Table("\"user\"")
// the quotes are required in the table name since user is a keyword in PostgreSQL and H2
data class UserEntity(
    @Id val id: Int?,
    val username: String,
    val firstName: String,
    val lastName: String
)
