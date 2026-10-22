package com.gblrod.orbitalnexus.service.impl

import com.gblrod.orbitalnexus.model.astronaut.Astronaut
import com.gblrod.orbitalnexus.model.mission.Mission
import com.gblrod.orbitalnexus.model.planet.Planet
import com.gblrod.orbitalnexus.repository.AstronautRepository
import com.gblrod.orbitalnexus.repository.MissionRepository
import com.gblrod.orbitalnexus.repository.PlanetRepository
import com.gblrod.orbitalnexus.util.Locales
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull

class MissionServiceImplTest {
    private lateinit var missionRepository: MissionRepository
    private lateinit var astronautRepository: AstronautRepository
    private lateinit var planetRepository: PlanetRepository
    private lateinit var service: MissionServiceImpl

    @BeforeEach
    fun setUp() {
        missionRepository = mockk()
        astronautRepository = mockk()
        planetRepository = mockk()

        service = MissionServiceImpl(
            missionRepository = missionRepository,
            astronautRepository = astronautRepository,
            planetRepository = planetRepository
        )
    }

    @Test
    fun shouldReturnMissionsWhenRequestedLocaleHasResults() {
        val locale = "pt-BR"
        val expectedMissions = listOf(mockk<Mission>())

        every {
            missionRepository.getAll(locale)
        } returns expectedMissions

        val result = service.getAll(locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getAll(locale)
        }

        verify(exactly = 0) {
            missionRepository.getAll(Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnDefaultLocaleMissionsWhenRequestedLocaleHasNoResults() {
        val locale = "pt-BR"
        val expectedMissions = listOf(mockk<Mission>())
        val emptyMissions = emptyList<Mission>()

        every {
            missionRepository.getAll(locale)
        } returns emptyMissions

        every {
            missionRepository.getAll(Locales.DEFAULT)
        } returns expectedMissions

        val result = service.getAll(locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getAll(locale)
        }

        verify(exactly = 1) {
            missionRepository.getAll(Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnEmptyListWhenNoMissionsExistForAnyLocale() {
        val locale = "fr-FR"
        val expectedMissions = emptyList<Mission>()

        every {
            missionRepository.getAll(locale)
        } returns expectedMissions

        every {
            missionRepository.getAll(Locales.DEFAULT)
        } returns expectedMissions

        val result = service.getAll(locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getAll(locale)
        }

        verify(exactly = 1) {
            missionRepository.getAll(Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnMissionWhenFoundInRequestedLocale() {
        val locale = "pt-BR"
        val id = 1
        val expectedMissions = mockk<Mission>()

        every {
            missionRepository.getById(id, locale)
        } returns expectedMissions

        val result = service.getById(id, locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getById(id, locale)
        }

        verify(exactly = 0) {
            missionRepository.getById(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnMissionFromDefaultLocaleWhenNotFoundInRequestedLocale() {
        val locale = "fr-FR"
        val id = 1
        val expectedMissions = mockk<Mission>()

        every {
            missionRepository.getById(id, locale)
        } returns null

        every {
            missionRepository.getById(id, Locales.DEFAULT)
        } returns expectedMissions

        val result = service.getById(id, locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getById(id, locale)
        }

        verify(exactly = 1) {
            missionRepository.getById(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnNullWhenMissionIsNotFoundInAnyLocale() {
        val locale = "fr-FR"
        val id = 123

        every {
            missionRepository.getById(id, locale)
        } returns null

        every {
            missionRepository.getById(id, Locales.DEFAULT)
        } returns null

        val result = service.getById(id, locale)
        assertNull(result)

        verify(exactly = 1) {
            missionRepository.getById(id, locale)
        }

        verify(exactly = 1) {
            missionRepository.getById(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnMissionsWhenFoundForPlanetInRequestedLocale() {
        val locale = "pt-BR"
        val id = 1
        val expectedMissions = listOf(mockk<Mission>())

        every {
            missionRepository.getByPlanetId(id, locale)
        } returns expectedMissions

        val result = service.getByPlanetId(id, locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getByPlanetId(id, locale)
        }

        verify(exactly = 0) {
            missionRepository.getByPlanetId(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnDefaultLocaleMissionsWhenPlanetHasNoMissionsInRequestedLocale() {
        val locale = "fr-FR"
        val id = 123
        val emptyMissions = emptyList<Mission>()
        val expectedMissions = listOf(mockk<Mission>())

        every {
            missionRepository.getByPlanetId(id, locale)
        } returns emptyMissions

        every {
            missionRepository.getByPlanetId(id, Locales.DEFAULT)
        } returns expectedMissions

        val result = service.getByPlanetId(id, locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getByPlanetId(id, locale)
        }

        verify(exactly = 1) {
            missionRepository.getByPlanetId(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnEmptyListWhenPlanetHasNoMissionsInAnyLocale() {
        val locale = "fr-FR"
        val id = 123
        val emptyMissions = emptyList<Mission>()
        val expectedMissions = emptyList<Mission>()

        every {
            missionRepository.getByPlanetId(id, locale)
        } returns emptyMissions

        every {
            missionRepository.getByPlanetId(id, Locales.DEFAULT)
        } returns expectedMissions

        val result = service.getByPlanetId(id, locale)
        assertEquals(expectedMissions, result)

        verify(exactly = 1) {
            missionRepository.getByPlanetId(id, locale)
        }

        verify(exactly = 1) {
            missionRepository.getByPlanetId(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnAstronautsAssociatedWithMission() {
        val id = 1
        val expectedAstronauts = listOf(mockk<Astronaut>())

        every {
            astronautRepository.getByMissionId(id)
        } returns expectedAstronauts

        val result = service.getAstronautsByMissionId(id)
        assertEquals(expectedAstronauts, result)

        verify(exactly = 1) {
            astronautRepository.getByMissionId(id)
        }
    }

    @Test
    fun shouldReturnEmptyListWhenMissionHasNoAstronauts() {
        val id = 123
        val expectedAstronauts = emptyList<Astronaut>()

        every {
            astronautRepository.getByMissionId(id)
        } returns expectedAstronauts

        val result = service.getAstronautsByMissionId(id)
        assertEquals(expectedAstronauts, result)

        verify(exactly = 1) {
            astronautRepository.getByMissionId(id)
        }
    }

    @Test
    fun shouldReturnPlanetAssociatedWithMission() {
        val locale = "pt-BR"
        val id = 1
        val missionId = 123
        val expectedPlanet = mockk<Planet>()
        val expectedMission = mockk<Mission>()

        every {
            missionRepository.getById(missionId, locale)
        } returns expectedMission

        every {
            expectedMission.planetId
        } returns id

        every {
            planetRepository.getById(id, locale)
        } returns expectedPlanet

        val result = service.getPlanetByMissionId(missionId, locale)
        assertEquals(expectedPlanet, result)

        verify(exactly = 1) {
            missionRepository.getById(missionId, locale)
        }

        verify(exactly = 1) {
            planetRepository.getById(id, locale)
        }
    }

    @Test
    fun shouldReturnNullWhenMissionDoesNotExist() {
        val locale = "pt-BR"
        val missionId = 123

        every {
            missionRepository.getById(missionId, locale)
        } returns null

        every {
            missionRepository.getById(missionId, Locales.DEFAULT)
        } returns null

        val result = service.getPlanetByMissionId(missionId, locale)
        assertNull(result)

        verify(exactly = 1) {
            missionRepository.getById(missionId, locale)
        }

        verify(exactly = 1) {
            missionRepository.getById(missionId, Locales.DEFAULT)
        }

        verify(exactly = 0) {
            planetRepository.getById(any(), any())
        }
    }

    @Test
    fun shouldReturnNullWhenPlanetDoesNotExist() {
        val locale = "pt-BR"
        val id = 1
        val missionId = 123
        val expectedMission = mockk<Mission>()

        every {
            missionRepository.getById(missionId, locale)
        } returns expectedMission

        every {
            expectedMission.planetId
        } returns id

        every {
            planetRepository.getById(id, locale)
        } returns null

        val result = service.getPlanetByMissionId(missionId, locale)
        assertNull(result)

        verify(exactly = 1) {
            missionRepository.getById(missionId, locale)
        }

        verify(exactly = 1) {
            planetRepository.getById(id, locale)
        }
    }
}