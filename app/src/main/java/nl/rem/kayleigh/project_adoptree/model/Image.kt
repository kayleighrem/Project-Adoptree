package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Image(
        val alt: String?,
        val createdAt: String?,
        val id: Int?,
        val image_blobname: String?,
        val tree_id: Int?
): Serializable