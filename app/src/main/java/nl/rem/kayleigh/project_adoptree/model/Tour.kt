package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable
import java.time.LocalDateTime

data class Tour(
    val dateTime: String?,
    val description: String?,
    val forestId: Int?,
    val guideName: String?,
    val guideSpecialty: String?,
    val id: Int?,
    val language: String?,
    val slots: Int?
): Serializable

data class BookedTour(
    val bookedDateTime: String?,
    val id: Int?,
    val tourId: Int?,
    val userEmail: String?,
    val userId: Int?,
    val userName: String?
): Serializable