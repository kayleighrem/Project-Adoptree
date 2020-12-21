package nl.rem.kayleigh.project_adoptree.model

import java.io.Serializable
import java.util.*

data class UserResponse(
    val username: String?,
    val AuthToken: String?
)

data class UserResponseRegister(
    val Success: Boolean?,
    val Message: String?
)

data class LoginResponse(
    val AuthToken: String?,
)

data class RegisterResponse(
    val Success: Boolean?,
    val Message: String?,
)

data class User(
    val createdAt: String?,
    val email: String?,
    val firstname: String?,
    val forgetToken: String?,
    val id: Int?,
    val lastname: String?,
    val password: String?,
    val role: RoleEnum?,
    val username: String?
): Serializable

enum class RoleEnum(
    ADMIN: Int = 0,
    ADOPTEE: Int = 1
): Serializable

data class ForgetPassword (
    val username: String?,
    val email: String
): Serializable

data class ResetPassword (
    val user_id: Int?,
    val token: String?,
    val created_at: String?,
    val valid_until: String?,
    val password: String?,
    val validate_password: String?
): Serializable