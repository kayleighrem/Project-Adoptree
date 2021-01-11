package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance
import nl.rem.kayleigh.project_adoptree.model.AssignedTree

class TreeRepository {
    suspend fun getAllTrees() = RetrofitInstance.api.getTrees()
    suspend fun getImagesByTreeId(id: Int) = RetrofitInstance.treeapi.getImagesByTreeId(id)
    suspend fun getTelemetryByTreeId(id: Int, token: String) = RetrofitInstance.treeapi.getTelemetryByTreeId(id, token)
    suspend fun getTreesByUserByTreeId(id: Int, token: String) = RetrofitInstance.treeapi.getTreesByUserByTreeId(token, id)
    suspend fun personalizeTree(token: String, assignedTree: AssignedTree) = RetrofitInstance.treeapi.personalizeTree(token, assignedTree)
}