package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance

class ContentRepository {
    suspend fun getAllContent() = RetrofitInstance.contentapi.getAllContent()
}