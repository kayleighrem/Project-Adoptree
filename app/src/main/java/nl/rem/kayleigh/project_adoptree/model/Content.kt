package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable

data class Content(
        val contentId: String?,
        val contentType: String?,
        val createdOn: String?,
        val id: String?,
        val text: String?,
        val title: String?
) : Serializable

enum class ContentTypeEnum (
        ABOUT: Int = 0,
        ANNOUNCEMENT: Int = 1,
        EVENT: Int = 2,
        INFORMATIVE: Int = 3
): Serializable