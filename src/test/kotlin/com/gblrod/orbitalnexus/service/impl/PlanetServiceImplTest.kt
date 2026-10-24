package com.gblrod.orbitalnexus.service.impl

import com.gblrod.orbitalnexus.model.planet.Planet
import com.gblrod.orbitalnexus.repository.PlanetRepository
import com.gblrod.orbitalnexus.util.Locales
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull

class PlanetServiceImplTest {
    private lateinit var repository: PlanetRepository
    private lateinit var service: PlanetServiceImpl

    @BeforeEach
    fun setUp() {
        repository = mockk()

        service = PlanetServiceImpl(
            repository = repository
        )
    }

    @Test
    fun shouldReturnPlanetsWhenRequestedLocaleHasResults() {
        val locale = "pt-BR"
        val expectedPlanets = listOf(mockk<Planet>())

        every {
            repository.getAll(locale)
        } returns expectedPlanets

        val result = service.getAll(locale)
        assertEquals(expectedPlanets, result)

        verify(exactly = 1) {
            repository.getAll(locale)
        }

        verify(exactly = 0) {
            repository.getAll(Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnDefaultLocalePlanetsWhenRequestedLocaleHasNoResults() {
        val locale = "fr-FR"
        val expectedPlanets = listOf(mockk<Planet>())
        val emptyPlanets = emptyList<Planet>()

        every {
            repository.getAll(locale)
        } returns emptyPlanets

        every {
            repository.getAll(Locales.DEFAULT)
        } returns expectedPlanets

        val result = service.getAll(locale)
        assertEquals(expectedPlanets, result)

        verify(exactly = 1) {
            repository.getAll(locale)
        }

        verify(exactly = 1) {
            repository.getAll(Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnEmptyListWhenNoPlanetsExistForAnyLocale() {
        val locale = "fr-FR"
        val emptyPlanets = emptyList<Planet>()

        every {
            repository.getAll(locale)
        } returns emptyPlanets

        every {
            repository.getAll(Locales.DEFAULT)
        } returns emptyPlanets

        val result = service.getAll(locale)
        assertEquals(emptyPlanets, result)

        verify(exactly = 1) {
            repository.getAll(locale)
        }

        verify(exactly = 1) {
            repository.getAll(Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnPlanetWhenFoundInRequestedLocale() {
        val locale = "pt-BR"
        val id = 1
        val expectedPlanets = mockk<Planet>()

        every {
            repository.getById(id, locale)
        } returns expectedPlanets

        val result = service.getById(id, locale)
        assertEquals(expectedPlanets, result)

        verify(exactly = 1) {
            repository.getById(id, locale)
        }

        verify(exactly = 0) {
            repository.getById(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnPlanetFromDefaultLocaleWhenNotFoundInRequestedLocale() {
        val locale = "fr-FR"
        val id = 1
        val expectedPlanets = mockk<Planet>()

        every {
            repository.getById(id, locale)
        } returns null

        every {
            repository.getById(id, Locales.DEFAULT)
        } returns expectedPlanets


        val result = service.getById(id, locale)
        assertEquals(expectedPlanets, result)

        verify(exactly = 1) {
            repository.getById(id, locale)
        }

        verify(exactly = 1) {
            repository.getById(id, Locales.DEFAULT)
        }
    }

    @Test
    fun shouldReturnNullWhenPlanetIsNotFoundInAnyLocale() {
        val locale = "fr-FR"
        val id = 1

        every {
            repository.getById(id, locale)
        } returns null

        every {
            repository.getById(id, Locales.DEFAULT)
        } returns null

        val result = service.getById(id, locale)
        assertNull(result)

        verify(exactly = 1) {
            repository.getById(id, locale)
        }

        verify(exactly = 1) {
            repository.getById(id, Locales.DEFAULT)
        }
    }
}