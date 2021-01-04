package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Tree(
    val id: Int?,
    val forestId: Int?,
    val productId: Int?,
    val health: Int?,
    val latitude: String?,
    val longitude: String?,
    val dateSeeded: String?,
    val assignedTree: AssignedTree?
) : Serializable

data class AssignedTree(
    val user_id: Int?,
    val tree_id: Int?,
    val order_id: Int?,
    val created_at: String?,
    val expire_date: String?,
    val tree_name: String?,
    val tree_color: String?
) : Serializable

data class TreeSpecies(
    val treeFactorAdjustment: Double?,
    val treeProductId: Int?,
    val treeWeightAlgorithmId: Int?
): Serializable

data class TreeWeightAlgorithm(
    val formulaAboveThreshold: String?,
    val formulaBelowThreshold: String?,
    val id: Int?,
    val sizeThreshold: Int?,
    val species: String?
): Serializable

data class TreeImage(
    val tree_id: Int,
    val images: List<Image>
)