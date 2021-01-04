package nl.rem.kayleigh.project_adoptree.model

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.*

data class UserResponse(
    val username: String,
    val AuthToken: String
)

data class UserLogin(
        val username: String,
        val password: String
)

data class LoginResponse(
        var accessToken: String = "Access key",
        var refreshToken: String = "Refresh token",
        var userId: String = "UserId"
)

//data class UserResponseRegister(
//    val Success: Boolean?,
//    val Message: String?
//)
//
//data class LoginResponse(
//    val AuthToken: String?,
//)
//
//data class RegisterResponse(
//    val Success: Boolean?,
//    val Message: String?,
//)

data class User(
        val id: Int?,
        val firstname: String?,
        val lastname: String?,
        val username: String?,
        val email: String?,
        val password: String?,
        val salt: String?,
        val forgetToken: String?,
        val role: RoleEnum?,
        val createdAt: String?,
): Serializable

data class UserResponseRegister(
    val Success: Boolean?,
    val Message: String?
)

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

data class RefreshTokenResponse (
    val accessToken: String,
    val refreshToken: String
)

data class UserShared (
    val id: Int?,
    val firstname: String?,
    val lastname: String?,
    val username: String?,
    val email: String?
)