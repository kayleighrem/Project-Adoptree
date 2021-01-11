package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Telemetry(
    val id: String?,
    val reports: List<Report>?,
    val treeId: String?
): Serializable

data class TreeTelemetry (
        var tree: Tree,
        var telemetry: Telemetry?
        )