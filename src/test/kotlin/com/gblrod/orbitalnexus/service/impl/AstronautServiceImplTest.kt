package com.gblrod.orbitalnexus.service.impl

import com.gblrod.orbitalnexus.model.astronaut.Astronaut
import com.gblrod.orbitalnexus.repository.AstronautRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull

class AstronautServiceImplTest {
    private lateinit var repository: AstronautRepository
    private lateinit var service: AstronautServiceImpl
    private val publicBaseUrl = "https://orbitalnexus-api.onrender.com"

    @BeforeEach
    fun setUp() {
        repository = mockk()

        service = AstronautServiceImpl(
            repository = repository,
            publicBaseUrl = publicBaseUrl
        )
    }

    @Test
    fun shouldReturnAstronautsWithPublicImageUrls() {
        val astronaut = createAstronaut()
        val expectedAstronaut = listOf(astronaut)

        every {
            repository.getAll()
        } returns expectedAstronaut

        val result = service.getAll()
        assertEquals(
            "https://orbitalnexus-api.onrender.com/assets/astronauts/Luna_Carter_image_profile.png",
            result.first().image
        )

        verify(exactly = 1) {
            repository.getAll()
        }
    }

    @Test
    fun shouldReturnAstronautsWithNullImagesWhenImageIsNull() {
        val astronaut = createAstronaut(image = null)

        val expectedAstronaut = listOf(astronaut)

        every {
            repository.getAll()
        } returns expectedAstronaut

        val result = service.getAll()
        assertNull(result.first().image)

        verify(exactly = 1) {
            repository.getAll()
        }
    }

    @Test
    fun shouldReturnEmptyListWhenNoAstronautsExist() {
        val emptyAstronauts = emptyList<Astronaut>()

        every {
            repository.getAll()
        } returns emptyAstronauts

        val result = service.getAll()
        assertEquals(emptyAstronauts, result)

        verify(exactly = 1) {
            repository.getAll()
        }
    }

    @Test
    fun shouldReturnAstronautWithPublicImageUrlWhenFound() {
        val id = 1
        val astronaut = createAstronaut()

        every {
            repository.getById(id)
        } returns astronaut

        val result = service.getById(id)
        assertEquals(
            "https://orbitalnexus-api.onrender.com/assets/astronauts/Luna_Carter_image_profile.png",
            result?.image
        )

        verify(exactly = 1) {
            repository.getById(id)
        }
    }

    @Test
    fun shouldReturnNullWhenAstronautIsNotFound() {
        val id = 123

        every {
            repository.getById(id)
        } returns null

        val result = service.getById(id)
        assertNull(result)

        verify(exactly = 1) {
            repository.getById(id)
        }
    }

    @Test
    fun shouldReturnAstronautWithNullImageWhenImageIsNull() {
        val id = 1
        val astronaut = createAstronaut(image = null)

        every {
            repository.getById(id)
        } returns astronaut

        val result = service.getById(id)
        assertEquals(astronaut, result)

        verify(exactly = 1) {
            repository.getById(id)
        }
    }

    @Test
    fun shouldReturnAstronautsWithPublicImageUrlsWhenMissionHasAstronauts() {
        val id = 1
        val astronaut = createAstronaut()

        val astronauts = listOf(astronaut)

        every {
            repository.getByMissionId(id)
        } returns astronauts

        val result = service.getByMissionId(id)
        assertEquals(
            "https://orbitalnexus-api.onrender.com/assets/astronauts/Luna_Carter_image_profile.png",
            result.first().image
        )

        verify(exactly = 1) {
            repository.getByMissionId(id)
        }
    }

    @Test
    fun shouldReturnEmptyListWhenMissionHasNoAstronauts() {
        val id = 123
        val astronauts = emptyList<Astronaut>()

        every {
            repository.getByMissionId(id)
        } returns astronauts

        val result = service.getByMissionId(id)
        assertEquals(astronauts, result)

        verify(exactly = 1) {
            repository.getByMissionId(id)
        }
    }

    private fun createAstronaut(
        id: Int = 1,
        name: String = "Luna Carter",
        nationality: String = "Terran",
        missionId: Int = 1,
        image: String? = "/assets/astronauts/Luna_Carter_image_profile.png"
    ): Astronaut {
        return Astronaut(
            id = id,
            name = name,
            nationality = nationality,
            missionId = missionId,
            image = image
        )
    }
}