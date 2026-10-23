package com.gblrod.orbitalnexus.service.impl

import com.gblrod.orbitalnexus.model.astronaut.Astronaut
import com.gblrod.orbitalnexus.repository.AstronautRepository
import com.gblrod.orbitalnexus.service.AstronautService

class AstronautServiceImpl(
    private val repository: AstronautRepository,
    private val publicBaseUrl: String
) : AstronautService {
    override fun getAll(): List<Astronaut> {
        return repository.getAll().map { it.withPublicImageUrl(publicBaseUrl) }
    }

    override fun getById(id: Int): Astronaut? {
        return repository.getById(id)?.withPublicImageUrl(publicBaseUrl)
    }

    override fun getByMissionId(
        missionId: Int
    ): List<Astronaut> {
        return repository.getByMissionId(missionId).map { it.withPublicImageUrl(publicBaseUrl) }
    }
}

private fun Astronaut.withPublicImageUrl(baseUrl: String): Astronaut {
    return copy(
        image = image?.let {
            "${baseUrl.trimEnd('/')}/${it.trimStart('/')}"
        }
    )
}