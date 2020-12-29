package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class VatRate(
        val countryId: Int?,
        val id: Int?,
        val rate: Int?,
        val type: String?
): Serializable