package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class NotificationDevice(
    val createdAt: String?,
    val deletedAt: String?,
    val deviceToken: String?,
    val id: Int?,
    val userId: Int?
): Serializable



