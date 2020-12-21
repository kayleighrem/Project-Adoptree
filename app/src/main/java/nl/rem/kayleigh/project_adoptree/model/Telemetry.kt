package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Telemetry(
    val id: String?,
    val reports: List<Report>?,
    val treeId: String?
): Serializable

data class Report(
    val humidity: Double?,
    val reportedOn: String?,
    val temperature: Double?,
    val treeDiameter: Double?,
    val treeLength: Double?
): Serializable