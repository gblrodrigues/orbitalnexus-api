package com.gblrod.orbitalnexus.routes

import com.gblrod.orbitalnexus.model.error.ErrorResponse
import com.gblrod.orbitalnexus.service.MissionService
import com.gblrod.orbitalnexus.service.PlanetService
import com.gblrod.orbitalnexus.util.locale
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.planetRoutes(
    planetService: PlanetService,
    missionService: MissionService
) {
    route("v1/planets") {
        get {
            val locale = call.locale()
            val planets = planetService.getAll(locale)
            call.respond(planets)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = planetIdInvalid())
                )
                return@get
            }

            val locale = call.locale()
            val planet = planetService.getById(id, locale)

            if (planet == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(message = planetIdNotFound(id))
                )
                return@get
            }

            call.respond(planet)
        }

        get("/{id}/missions") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = planetIdInvalid())
                )
                return@get
            }

            val locale = call.locale()
            val planet = planetService.getById(id, locale)

            if (planet == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(message = planetIdNotFound(id))
                )
                return@get
            }

            val missionsByPlanet = missionService.getByPlanetId(id, locale)

            call.respond(missionsByPlanet)
        }
    }
}

private fun planetIdNotFound(id: Int): String = "Planet with id '$id' not found"
private fun planetIdInvalid(): String = "Invalid planet id"