package com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins

import com.tomaszezula.kotlin101.sealed.tutorial.example04.service.ServiceResult
import com.tomaszezula.kotlin101.sealed.tutorial.example04.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Application.configureRouting(userService: UserService) {
    routing {
        route("/api/v1/users") {
            get("/{userId}") {
                call.parameters["userId"]?.let { userId ->
                    when (val result = userService.getUser(userId)) {
                        is ServiceResult.Success -> call.respond(HttpStatusCode.OK, result.data)
                        is ServiceResult.Failure -> mapFailure(result)
                    }
                } ?: call.respond(HttpStatusCode.BadRequest)
            }
            post {
                val email = call.parameters["email"]
                val name = call.parameters["name"]
                if (email == null || name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    when (val result = userService.registerUser(email, name)) {
                        is ServiceResult.Success -> call.respond(HttpStatusCode.Created, result.data)
                        is ServiceResult.Failure -> mapFailure(result)
                    }
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.mapFailure(
    result: ServiceResult.Failure
) {
    val status = when (result.status) {
        400 -> HttpStatusCode.BadRequest
        401, 403, 405 -> HttpStatusCode.Unauthorized
        404 -> HttpStatusCode.NotFound
        429 -> HttpStatusCode.TooManyRequests
        else -> HttpStatusCode.InternalServerError
    }
    call.respond(status)
}
