package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance

class TreeRepository {
    suspend fun getAllTrees(notassigned: String) = RetrofitInstance.api.getAllTrees(notassigned)
//    fun getTree() = RetrofitInstance.api.getAllTrees()
}