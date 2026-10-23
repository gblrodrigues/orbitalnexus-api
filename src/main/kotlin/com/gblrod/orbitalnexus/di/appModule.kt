package com.gblrod.orbitalnexus.di

import com.gblrod.orbitalnexus.repository.AstronautRepository
import com.gblrod.orbitalnexus.repository.MissionRepository
import com.gblrod.orbitalnexus.repository.PlanetRepository
import com.gblrod.orbitalnexus.repository.impl.AstronautRepositoryImpl
import com.gblrod.orbitalnexus.repository.impl.MissionRepositoryImpl
import com.gblrod.orbitalnexus.repository.impl.PlanetRepositoryImpl
import com.gblrod.orbitalnexus.service.AstronautService
import com.gblrod.orbitalnexus.service.MissionService
import com.gblrod.orbitalnexus.service.PlanetService
import com.gblrod.orbitalnexus.service.impl.AstronautServiceImpl
import com.gblrod.orbitalnexus.service.impl.MissionServiceImpl
import com.gblrod.orbitalnexus.service.impl.PlanetServiceImpl
import org.koin.dsl.module

fun appModule(publicBaseUrl: String) = module {
    // Planet
    single<PlanetRepository> {
        PlanetRepositoryImpl()
    }

    single<PlanetService> {
        PlanetServiceImpl(
            repository = get()
        )
    }

    // Mission
    single<MissionRepository> {
        MissionRepositoryImpl()
    }

    single<MissionService> {
        MissionServiceImpl(
            missionRepository = get(),
            planetRepository = get(),
            astronautRepository = get()
        )
    }

    // Astronaut
    single<AstronautRepository> {
        AstronautRepositoryImpl()
    }

    single<AstronautService> {
        AstronautServiceImpl(
            repository = get(),
            publicBaseUrl = publicBaseUrl
        )
    }
}