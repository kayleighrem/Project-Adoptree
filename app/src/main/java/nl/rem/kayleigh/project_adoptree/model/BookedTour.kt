package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class BookedTour(
        val bookedDateTime: String?,
        val id: Int?,
        val tourId: Int?,
        val userEmail: String?,
        val userId: Int?,
        val userName: String?
): Serializable