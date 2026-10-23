package com.gblrod.orbitalnexus.routes

import com.gblrod.orbitalnexus.model.error.ErrorResponse
import com.gblrod.orbitalnexus.service.MissionService
import com.gblrod.orbitalnexus.util.locale
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.missionRoutes(
    missionService: MissionService
) {
    route("v1/missions") {
        get {
            val locale = call.locale()
            val missions = missionService.getAll(locale)
            call.respond(missions)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = missionIdInvalid())
                )
                return@get
            }

            val locale = call.locale()
            val mission = missionService.getById(id, locale)

            if (mission != null) {
                call.respond(mission)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(message = missionIdNotFound(id))
                )
            }
        }

        get("/{id}/astronauts") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = missionIdInvalid())
                )
                return@get
            }

            val locale = call.locale()
            val mission = missionService.getById(id, locale)

            if (mission == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(message = missionIdNotFound(id))
                )
                return@get
            }

            val astronaut = missionService.getAstronautsByMissionId(id)

            call.respond(astronaut)
        }

        get("/{id}/planet") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = missionIdInvalid())
                )
                return@get
            }

            val locale = call.locale()
            val planet = missionService.getPlanetByMissionId(id, locale)

            if (planet == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(message = missionOrPlanetNotFound(id))
                )
                return@get
            }

            call.respond(planet)
        }
    }
}

private fun missionIdNotFound(id: Int): String = "Mission with id '$id' not found"
private fun missionOrPlanetNotFound(id: Int): String = "Mission with id '$id' not found, or its associated planet was not found"
private fun missionIdInvalid(): String = "Invalid mission id"