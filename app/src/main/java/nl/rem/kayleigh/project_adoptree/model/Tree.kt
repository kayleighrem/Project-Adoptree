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
        var tree_name: String?,
        val tree_color: String?
) : Serializable

data class TreeObjectCombined(
        val tree: Tree,
        val forest: Forest?,
        val wildlife: Wildlife?,
        val telemetry: Telemetry,
        val sequestration: Double?

)