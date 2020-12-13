package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance

class TreeRepository {
    suspend fun getAllTrees() = RetrofitInstance.api.getAvailableTrees()
//    fun getTree() = RetrofitInstance.api.getAllTrees()
}