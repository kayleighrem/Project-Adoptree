package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance

class AdoptionRepository {
    suspend fun getAvailableTrees() = RetrofitInstance.api.getAvailableTrees()
}