package com.example.api.cucumber.repositories

import com.example.api.data.user.entities.UserEntity
import com.example.api.data.user.repositories.UserRepository
import io.cucumber.datatable.DataTable
import io.cucumber.java.After
import io.cucumber.java.DataTableType
import io.cucumber.java.en.Given
import kotlinx.coroutines.runBlocking
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await

class UserRepositoryStepDefs(
    private val userRepository: UserRepository,
    private val databaseClient: DatabaseClient
) {
    @Given("the following users exist")
    fun theFollowingUsersExist(table: DataTable) {
        val storedUsers = table.tableConverter.toList<UserEntity>(table, UserEntity::class.java)

        runBlocking {
            storedUsers.forEach { userEntity ->
                userRepository.save(userEntity)
            }
        }
    }

    @After("@users")
    fun teardown() {
        runBlocking {
            databaseClient.sql("TRUNCATE TABLE \"user\" RESTART IDENTITY").await()
        }
    }

    @DataTableType
    fun mapUserEntity(tableRow: Map<String, String>): UserEntity {
        return UserEntity(
            id = null,
            firstName = tableRow["firstName"].toString(),
            lastName = tableRow["lastName"].toString(),
            username = tableRow["username"].toString()
        )
    }
}
