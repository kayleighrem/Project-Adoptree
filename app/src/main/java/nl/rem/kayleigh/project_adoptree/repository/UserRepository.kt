package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance
import nl.rem.kayleigh.project_adoptree.model.User
import retrofit2.http.Field

class UserRepository {
    suspend fun login(username: String, password: String) = RetrofitInstance.userapi.login(username, password)
    suspend fun login(user: User) = RetrofitInstance.userapi.login(user)
    suspend fun createUser(user: User) = RetrofitInstance.userapi.createUser(user)
    suspend fun forgotpassword(username: String, email: String) = RetrofitInstance.userapi.forgotpassword(username, email)
    suspend fun resetpassword(token: String, password: String, validate_password: String) = RetrofitInstance.userapi.resetpassword(token, password, validate_password)
    suspend fun getTreesByUser(id: Int) = RetrofitInstance.userapi.getTreesByUser(id)
    suspend fun createUser(firstname: String,lastname: String,username: String,email: String,password: String) = RetrofitInstance.userapi.createUser(firstname, lastname, username, email, password)
    suspend fun updateUser(firstname: String, lastname: String, username: String, email: String, password: String) = RetrofitInstance.userapi.updateUser(firstname, lastname, username, email, password)
}