package com.gblrod.orbitalnexus.plugins

import com.gblrod.orbitalnexus.di.appModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    val publicBaseUrl = environment.config
        .property("app.publicBaseUrl")
        .getString()

    install(Koin) {
        modules(appModule(publicBaseUrl))
    }
}