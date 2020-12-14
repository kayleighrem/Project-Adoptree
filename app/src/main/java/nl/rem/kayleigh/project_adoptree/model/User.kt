package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable
import java.util.*

data class User(
    val id: UUID?,
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val password: String,
    val forgetToken: String?,
    val role: Role?,
    val createdAt: String?
) : Serializable

enum class Role(
    Admin: Int = 0,
    Adoptee: Int = 1
)

data class UserResponse(
    val username: String?,
    val AuthToken: String?
)

data class UserResponseRegister(
    val Success: Boolean?,
    val Message: String?
)

data class LoginResponse(
    val AuthToken: String,
)

data class RegisterResponse(
    val Success: Boolean,
    val Message: String,
)