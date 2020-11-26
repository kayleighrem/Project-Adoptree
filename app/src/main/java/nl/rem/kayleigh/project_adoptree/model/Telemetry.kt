package nl.rem.kayleigh.project_adoptree.model

data class Telemetry(
    val id: Int,
    val treeId: Tree,
    val reports: Report
)

data class Report(
    val reportedOn: String,
    val temperature: Double,
    val humidity: Double,
    val treeLength: Double,
    val treeDiameter: Double
)