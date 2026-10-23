package com.gblrod.orbitalnexus.routes

import com.gblrod.orbitalnexus.model.error.ErrorResponse
import com.gblrod.orbitalnexus.service.AstronautService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.astronautRoutes(
    astronautService: AstronautService
) {
    route("v1/astronauts") {
        get {
            val astronauts = astronautService.getAll()
            call.respond(astronauts)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = astronautIdInvalid())
                )
                return@get
            }

            val astronaut = astronautService.getById(id)

            if (astronaut != null) {
                call.respond(astronaut)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(message = astronautIdNotFound(id))
                )
            }
        }
    }
}

private fun astronautIdNotFound(id: Int): String = "Astronaut with id '$id' not found"
private fun astronautIdInvalid(): String = "Invalid astronaut id"