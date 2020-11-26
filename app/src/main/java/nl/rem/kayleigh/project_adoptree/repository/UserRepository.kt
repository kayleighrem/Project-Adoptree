package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance
import nl.rem.kayleigh.project_adoptree.model.User

class UserRepository {
    suspend fun login(user: User) = RetrofitInstance.api.login(user)

    suspend fun createUser(user: User) = RetrofitInstance.api.createUser(user)
}