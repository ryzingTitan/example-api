package com.example.api.data.user.repositories

import com.example.api.data.user.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<UserEntity, Int> {
    fun findAllByFirstName(firstName: String): Flow<UserEntity>
}
