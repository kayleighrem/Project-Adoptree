package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Tree(
    val id: Int,
    val forestId: Int,
    val productId: Int,
    val health: Int,
    val latitude: String,
    val longitude: String,
    val dateSeeded: String,
    val assignedTree: AssignedTree
) : Serializable

data class AssignedTree(
    val user_id: User,
    val tree_Id: Int,
    val order_id: Int,
    val created_at: String,
    val expire_date: String,
    val tree_name: String,
    val tree_color: String
)

data class TreeSign(
    val id: Int,
    val tree_id: Tree,
    val product_id: Int,
    val sign_id: Int,
    val order_id: Int,
    val createdAt: String,
    val deletedAt: String
)

data class TreeSpecies(
    val treeProductId: Int,
    val treeWeightAlgorithmId: Int,
    val treeFactorAdjustment: Double
)

