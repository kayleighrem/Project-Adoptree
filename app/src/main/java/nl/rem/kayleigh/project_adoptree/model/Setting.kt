package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Setting(
        val description: String?,
        val id: Int?,
        val name: String?,
        val value: String?
): Serializable