package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class TreeSign(
        val createdAt: String?,
        val deletedAt: String?,
        val id: Int?,
        val order_id: Int?,
        val product_id: Int?,
        val sign_text: String?,
        val tree_id: Int?
): Serializable