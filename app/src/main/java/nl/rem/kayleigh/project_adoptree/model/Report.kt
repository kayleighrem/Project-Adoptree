package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Report(
        val humidity: Double?,
        val reportedOn: String?,
        val temperature: Double?,
        val treeDiameter: Double?,
        val treeLength: Double?
): Serializable