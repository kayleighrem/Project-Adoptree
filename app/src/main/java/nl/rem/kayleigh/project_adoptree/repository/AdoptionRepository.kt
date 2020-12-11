package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.model.TreeResult
import retrofit2.Response

class AdoptionRepository {
    suspend fun getAllTrees(): TreeResult = RetrofitInstance.api.getAllTrees()
}