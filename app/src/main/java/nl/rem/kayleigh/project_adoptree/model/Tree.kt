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

data class Country(
    val id: Int?,
    val name: String?
): Serializable

data class Forest(
    val countryId: Int?,
    val id: Int?,
    val latitude: String?,
    val longitude: String?,
    val name: String?,
    val xLength: Int?,
    val yLength: Int?
): Serializable

data class TreeSign(
    val createdAt: String?,
    val deletedAt: String?,
    val id: Int?,
    val order_id: Int?,
    val product_id: Int?,
    val sign_text: String?,
    val tree_id: Int?
): Serializable

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

data class Wildlife(
    val description: String?,
    val id: Int?,
    val name: String?
): Serializable