package nl.rem.kayleigh.project_adoptree.model

import java.time.LocalDateTime

data class Tour(
    val id: Int,
    val description: String,
    val dateTime: LocalDateTime,
    val forestId: Forest,
    val slots: Int,
    val language: String,
    val guideName: String,
    val guideSpecialty: String
)

data class BookedTour(
    val id: Int,
    val tourId: Tour,
    val userId: User,
    val userName: User,
    val userEmail: User,
    val bookedDateTime: LocalDateTime
)

data class Forest(
    val id: Int,
    val name: String,
    val countryId: Country,
    val xLength: Int,
    val yLength: Int,
    val latitude: String,
    val longitude: String
)

data class Country(
    val id: Int,
    val name: String
)