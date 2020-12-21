package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Content(
    val contentId: String?,
    val contentType: ContentTypeEnum?,
    val createdOn: String?,
    val id: String?,
    val text: String?,
    val title: String?
) : Serializable

data class Image(
    val alt: String?,
    val createdAt: String?,
    val id: Int?,
    val image_blobname: String?,
    val tree_id: Int?
): Serializable

data class NotificationDevice(
    val createdAt: String?,
    val deletedAt: String?,
    val deviceToken: String?,
    val id: Int?,
    val userId: Int?
): Serializable

data class Setting(
    val description: String?,
    val id: Int?,
    val name: String?,
    val value: String?
): Serializable

enum class ContentTypeEnum (
    ABOUT: Int = 0,
    ANNOUNCEMENT: Int = 1,
    EVENT: Int = 2,
    INFORMATIVE: Int = 3
): Serializable