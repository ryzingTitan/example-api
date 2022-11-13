package com.example.api.cucumber.controllers

import com.example.api.cucumber.common.CommonControllerStepDefs.CommonControllerStepDefsSharedState.responseStatus
import com.example.api.domain.dtos.User
import io.cucumber.datatable.DataTable
import io.cucumber.java.Before
import io.cucumber.java.DataTableType
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitEntity
import org.springframework.web.reactive.function.client.awaitEntityList
import org.springframework.web.reactive.function.client.awaitExchange

class UserControllerStepDefs {
    @Before("@users")
    fun setup() {
        webClient = WebClient.create("http://localhost:$port/api/users")
    }

    @When("a request is made to find a user with id {int}")
    fun aRequestIsMadeToFindUserWithId(userId: Int) {
        runBlocking {
            webClient.get()
                .uri("/$userId")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange { clientResponse ->
                    handleSingleUserResponse(clientResponse)
                }
        }
    }

    @When("a request is made to find all users with first name {string}")
    fun aRequestIsMadeToFindAllUsersWithFirstName(firstName: String) {
        runBlocking {
            webClient.get()
                .uri("?firstName=$firstName")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange { clientResponse ->
                    handleMultipleUserResponse(clientResponse)
                }
        }
    }

    @When("a request is made with no first name parameter")
    fun aRequestIsMadeWithNoFirstNameParameter() {
        runBlocking {
            webClient.get()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange { clientResponse ->
                    handleMultipleUserResponse(clientResponse)
                }
        }
    }

    @Then("the following users are returned")
    fun theFollowingUsersAreReturned(table: DataTable) {
        val expectedUsers = table.tableConverter.toList<User>(table, User::class.java)

        assertEquals(expectedUsers.sortedBy { it.id }, returnedUsers.sortedBy { it.id })
    }

    @DataTableType
    fun mapUser(tableRow: Map<String, String>): User {
        return User(
            id = tableRow["id"]?.toInt() ?: 0,
            firstName = tableRow["firstName"].toString(),
            lastName = tableRow["lastName"].toString(),
            fullName = tableRow["fullName"].toString()
        )
    }

    private suspend fun handleSingleUserResponse(clientResponse: ClientResponse) {
        responseStatus = clientResponse.statusCode()

        if (clientResponse.statusCode() == HttpStatus.OK) {
            val user = clientResponse.awaitEntity<User>().body

            if (user != null)
                returnedUsers.add(user)
        }
    }

    private suspend fun handleMultipleUserResponse(clientResponse: ClientResponse) {
        responseStatus = clientResponse.statusCode()

        if (clientResponse.statusCode() == HttpStatus.OK) {
            val userList = clientResponse.awaitEntityList<User>().body

            if (userList != null)
                returnedUsers.addAll(userList)
        }
    }

    @LocalServerPort
    private val port = 0

    private lateinit var webClient: WebClient

    private val returnedUsers = mutableListOf<User>()
}
