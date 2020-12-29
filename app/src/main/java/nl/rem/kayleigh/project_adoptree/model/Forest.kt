package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Forest(
        val countryId: Int?,
        val id: Int?,
        val latitude: String?,
        val longitude: String?,
        val name: String?,
        val xLength: Int?,
        val yLength: Int?
): Serializable